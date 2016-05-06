
package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.authWeb.app.security.EcontactPrincipal;
import com.econtact.authWeb.app.utils.CacheUtils;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.access.AccessGroupEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

/**
 * User session bean.
 * @author Yevgen Riaboshtan
 *
 */
@Named
@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 5815150040159902787L;
	
	/**
	 * Authentication Service instance. {@link AuthenticationService}
	 */
	@EJB
	private AuthenticationService authenticatedService;
	
	/**
	 * Generic Service instance. {@link GenericService}
	 */
	@EJB
	private GenericService genericService;
	
	/**
	 * System Principal of the current user. {@link EcontactPrincipal}
	 */
	private EcontactPrincipal principal;
	/**
	 * User context of the current user. {@link UserContext}
	 */
	private UserContext userContext;
	/**
	 * Session id of the current user session.
	 */
	private String sessionId;
	
	//FIXME need add normal permissions cache
	/**
	 * Access to the church. {@link AccessChurchEntity}
	 */
	private Map<BigDecimal, AccessChurchEntity> churchAccess;
	/**
	 * Access to the group. {@link AccessGroupEntity}
	 */
	private Map<BigDecimal, AccessGroupEntity> groupAccess;
	
	/**
	 * Bean initial method.
	 * @see PostConstruct
	 */
	@PostConstruct
	public void init() {
		principal = (EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userContext = UserContext.create(getSessionUser(), TimeZone.getTimeZone("GMT+2"));
		sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
	}
	
	/**
	 * Method write user logout time.
	 * @see PreDestroy
	 */
	@PreDestroy
	public void disconnect() {
		authenticatedService.disconnectUser(sessionId);
	}
	
	/**
	 * Method return current session user entity.
	 * @return - current session user entity {@link SessionUserEntity}.
	 */
	public SessionUserEntity getSessionUser() {
		return principal.getUserAccount();
	}
	
	/**
	 * Method return available churches for current user. 
	 * @return - available church {@link ChurchEntity} list 
	 */
	public List<ChurchEntity> getAvailableChurchs() {
		return principal.getAvailableChurchs();
	}
	
	/**
	 * Method return - if user work in admin mode.
	 * Only users with {@link RoleType#ROLE_ADMIN} role can work in admin mode.
	 * @return - true if user work in admin mode.
	 */
	public boolean isAdminMode() {
		return principal.isAdminMode();
	}
	
	/**
	 * Method return - if user work in sysAdmin mode.
	 * Only users with {@link RoleType#ROLE_SUPER_ADMIN} role can work in sysAdmin mode.
	 * @return - true if user work in sysAdmin mode.
	 */
	public boolean isSysAdminMode() {
		return principal.isSysAdminMode();
	}
	
	/**
	 * Method return selected current church, can be null if user have {@link RoleType#ROLE_SUPER_ADMIN} role or work in administrator mode.
	 * @return - selected current church {@link ChurchEntity}.
	 */
	public ChurchEntity getCurrentChurch() {
		return principal.getSelectedChurch();
	}
	
	/**
	 * Method return context of the current user.
	 * @return - current user context {@link UserContext}.
	 */
	public UserContext getUserContext() {
		return userContext;
	}
	
	/**
	 * Check if user have access to the church
	 * @param idChurch - church id to check
	 * @return Access to the church {@link AccessChurchEntity} if exist or null.
	 */
	public AccessChurchEntity getChurchAccess(BigDecimal idChurch) {
		if (churchAccess == null
				|| CacheUtils.needClearChurchAccess(getSessionUser().getId())) {
			SearchCriteria<AccessChurchEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccessChurchEntity.class));
			criteria.andFilter(new FilterDefEquals(AccessChurchEntity.CONFIRM_A, true))
					.andFilter(new FilterDefEquals(AccessChurchEntity.USER_A, getSessionUser()))
					.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
			churchAccess = genericService.find(criteria)
					.stream().collect(Collectors.toMap( item -> ((AccessChurchEntity) item).getChurch().getId(), Function.identity()));
		}
		return 	churchAccess.get(idChurch);
	}
	
	/**
	 * Check if user have access to the group
	 * @param idGroup - group id to check
	 * @return Access to the group {@link AccessGroupEntity} if exist or null.
	 */
	public AccessGroupEntity getGroupAccess(BigDecimal idGroup) {
		if (groupAccess == null
				|| CacheUtils.needClearGroupAccess(getSessionUser().getId())) {
			SearchCriteria<AccessGroupEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccessGroupEntity.class));
			criteria.andFilter(new FilterDefEquals(AccessGroupEntity.CONFIRM_A, true))
					.andFilter(new FilterDefEquals(AccessGroupEntity.USER_A, getSessionUser()))
					.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
			groupAccess = genericService.find(criteria, AccessGroupEntity.ACCESS_GROUP_GRAPH)
					.stream().collect(Collectors.toMap(item -> ((AccessGroupEntity) item).getGroup().getId(), Function.identity()));
		}
		return groupAccess.get(idGroup);
	}
}
