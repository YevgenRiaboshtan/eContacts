package com.econtact.authWeb.app.beans.view.church;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;

import org.primefaces.event.RowEditEvent;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.filter.FilterDefNotInList;
import com.econtact.dataModel.data.filter.FilterDefStartsWith;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.ChurchService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@ManagedBean (name = "churchCRUDBean")
@ViewScoped
public class ChurchCRUDBean extends GeneralCRUDBean<ChurchEntity> {
	private static final long serialVersionUID = 5261936332118028517L;
	
	private Set<AccessChurchEntity> accesses;
	private Set<BigDecimal> toRemove = new HashSet<BigDecimal>();
	
	private SessionUserEntity accessUser;
	
	public SessionUserEntity getAccessUser() {
		return accessUser;
	}

	public void setAccessUser(SessionUserEntity accessUser) {
		this.accessUser = accessUser;
	}

	public Set<AccessChurchEntity> getAccesses() {
		return accesses;
	}

	public void setAccesses(Set<AccessChurchEntity> accesses) {
		this.accesses = accesses;
	}

	public void onAccessEdit(@NotNull RowEditEvent event) {
		accesses.add((AccessChurchEntity) event.getObject());
	}
	
	public List<SessionUserEntity> accessUserComplete(String login) {
		List<BigDecimal> existIds = new ArrayList<BigDecimal>();
		accesses.forEach(access -> existIds.add(access.getUser().getId()));
		SearchCriteria<SessionUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(SessionUserEntity.class));
		criteria.andFilter(new FilterDefStartsWith(SessionUserEntity.LOGIN_A, login))
				.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN))
				.andFilter(new FilterDefNotInList(EntityHelper.ID_A, existIds));
		return genericService.find(criteria, 0, 10);
	}
	
	public void removeAccessUser(@NotNull AccessChurchEntity item) {
		accesses.remove(item);
		if (item.getId() != null) {
			toRemove.add(item.getId());
		}
	}
	
	public void addAccessUser() {
		//FIXME очищать кеш после выбора или удалять толкьо выбранные элемент
		if (accessUser != null) {
			AccessChurchEntity access = new AccessChurchEntity();
			access.setChurch(entity);
			access.setUser(accessUser);
			accesses.add(access);
			accessUser = null;
		}
	}
	
	@Override
	protected boolean canModifyEntity(ChurchEntity entity) {
		if (EntityHelper.ACTUAL_SIGN.equals(entity.getSign())
				&& userSession.getPrincipal().equals(entity.getOwner())) {
			return true;
		}
		return false;
	}

	@Override
	public void setEntity(ChurchEntity entity) {
		super.setEntity(entity);
		if (entity.getId() != null) {
			SearchCriteria<AccessChurchEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccessChurchEntity.class));
			criteria.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN))
					.andFilter(new FilterDefEquals(AccessChurchEntity.CHURCH_A, getEntity()));
			accesses = new HashSet<AccessChurchEntity>(genericService.find(criteria));
		} else {
			accesses = new HashSet<AccessChurchEntity>();
		}
	}
	
	@Override
	protected ChurchEntity createDefaultEntity() {
		ChurchEntity entity = new ChurchEntity();
		entity.setOwner(userSession.getPrincipal());
		entity.setCreateDate(new Date());
		return entity;
	}
	
	protected ChurchEntity saveorUpdate(ChurchEntity entity, UserContext userContext) throws UniqueConstraintException {
		return WebUtils.getBean(ChurchService.class).saveOrUpdate(entity, userContext);
	}
	
	@Override
	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate("/admin/church/list.jsf");
	}
	
	@Override
	protected void cancelNavigate() throws IOException {
		navigationHelper.navigate("/admin/church/list.jsf");
	}
}
