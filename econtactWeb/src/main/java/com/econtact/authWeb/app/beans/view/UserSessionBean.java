
package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Named
@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 5815150040159902787L;
	
	@EJB
	private AuthenticationService authenticatedService;
	
	@EJB
	private GenericService genericService;
	
	private EcontactPrincipal principal;
	private UserContext userContext;
	private String sessionId;
	
	private Map<BigDecimal, AccessChurchEntity> churchAccess;
	private Map<BigDecimal, AccessGroupEntity> groupAccess;
	
	@PostConstruct
	public void init() {
		principal = (EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userContext = UserContext.create(getPrincipal(), TimeZone.getTimeZone("GMT+2"));
		sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
	}
	
	@PreDestroy
	public void disconnect() {
		authenticatedService.disconnectUser(sessionId);
	}
	
	public SessionUserEntity getPrincipal() {
		return principal.getUserAccount();
	}
	
	public UserContext getUserContext() {
		return userContext;
	}
	
	public AccessChurchEntity getChurchAccess(BigDecimal idChurch) {
		if (churchAccess == null) {
			SearchCriteria<AccessChurchEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccessChurchEntity.class));
			criteria.andFilter(new FilterDefEquals(AccessChurchEntity.CONFIRM_A, true))
					.andFilter(new FilterDefEquals(AccessChurchEntity.USER_A, getPrincipal()))
					.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
			churchAccess = genericService.find(criteria)
					.stream().collect(Collectors.toMap( item -> ((AccessChurchEntity) item).getChurch().getId(), Function.identity()));
		}
		return 	churchAccess.get(idChurch);
	}
	
	public AccessGroupEntity getGroupAccess(BigDecimal idGroup) {
		if (groupAccess == null
				|| CacheUtils.needClearGroupAccess(principal.getUserAccount().getId())) {
			SearchCriteria<AccessGroupEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccessGroupEntity.class));
			criteria.andFilter(new FilterDefEquals(AccessGroupEntity.CONFIRM_A, true))
					.andFilter(new FilterDefEquals(AccessGroupEntity.USER_A, getPrincipal()))
					.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
			groupAccess = genericService.find(criteria, AccessGroupEntity.ACCESS_GROUP_GRAPH)
					.stream().collect(Collectors.toMap(item -> ((AccessGroupEntity) item).getGroup().getId(), Function.identity()));
		}
		return groupAccess.get(idGroup);
	}
}
