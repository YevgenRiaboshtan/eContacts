package com.econtact.authWeb.app.beans.view.church;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.econtact.authWeb.app.beans.view.AbstractCRUDBean;
import com.econtact.authWeb.app.utils.CacheUtils;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.filter.FilterDefNotInList;
import com.econtact.dataModel.data.filter.FilterDefOr;
import com.econtact.dataModel.data.filter.FilterDefStartsWith;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.access.AccessGroupEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

@ManagedBean(name = "churchCRUDBean")
@ViewScoped
public class ChurchCRUDBean extends AbstractCRUDBean<ChurchEntity> {
	private static final long serialVersionUID = 5261936332118028517L;

	private SessionUserEntity accessUser;
	private String groupName;

	//FIXME dublicate functionality GroupCRUDBean
	public List<SessionUserEntity> accessUserComplete(String login) {
		List<BigDecimal> existIds = new ArrayList<BigDecimal>();
		entity.getAccess().forEach(access -> existIds.add(access.getUser().getId()));
		existIds.add(userSessionBean.getSessionUser().getId());
		existIds.add(entity.getOwner().getId());
		SearchCriteria<SessionUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(
				SessionUserEntity.class));
		criteria.andFilter(new FilterDefStartsWith(SessionUserEntity.LOGIN_A, login))
				.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN))
				.andFilter(new FilterDefOr(
						new FilterDefEquals(SessionUserEntity.ROLE_A, RoleType.ROLE_ADMIN),
						new FilterDefEquals(SessionUserEntity.ROLE_A, RoleType.ROLE_EMPLOYEE)))
				.andFilter(new FilterDefNotInList(EntityHelper.ID_A, existIds));
		return genericService.find(criteria, 0, 10);
	}

	public void addAccessUser() {
		if (accessUser != null
				&& entity.getAccess().stream().noneMatch(item -> item.getUser().equals(accessUser))) {
			AccessChurchEntity access = new AccessChurchEntity();
			access.setUser(accessUser);
			entity.addAccess(access);
		}
		accessUser = null;
	}

	public void removeAccessUser(@NotNull AccessChurchEntity item) {
		entity.removeAccess(item);
	}

	public void addGroup() {
		if (StringUtils.isNotBlank(groupName)) {
			GroupEntity group = new GroupEntity();
			group.setName(groupName);
			AccessGroupEntity groupAccess = new AccessGroupEntity();
			groupAccess.setUser(userSessionBean.getSessionUser());
			groupAccess.setConfirm(true);
			groupAccess.setEditPermit(true);
			groupAccess.setRegisterPermit(true);
			groupAccess.setViewPermit(true);
			group.addAccess(groupAccess);
			if (!entity.getOwner().equals(userSessionBean.getSessionUser())) {
				AccessGroupEntity ownerAccess = new AccessGroupEntity();
				ownerAccess.setUser(entity.getOwner());
				ownerAccess.setConfirm(true);
				ownerAccess.setEditPermit(true);
				ownerAccess.setRegisterPermit(true);
				ownerAccess.setViewPermit(true);
				group.addAccess(ownerAccess);
			}
			entity.addGroup(group);
			groupName = "";
		}
	}

	public void removeGroup(@NotNull GroupEntity item) {
		entity.removeAccess(item);
	}

	@Override
	protected boolean canModifyEntity(ChurchEntity entity) {
		if (EntityHelper.ACTUAL_SIGN.equals(entity.getSign())
				&& userSessionBean.getChurchAccess(entity.getId()) != null 
				&& userSessionBean.getChurchAccess(entity.getId()).isEditPermit()) {
			return true;
		}
		return false;
	}

	@Override
	public void setEntity(ChurchEntity entity) {
		if (entity.getId() != null) {
			SearchCriteria<AccessChurchEntity> accessCriteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccessChurchEntity.class));
			accessCriteria.andFilter(new FilterDefEquals(AccessChurchEntity.CHURCH_A, entity)).andFilter(
					new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
			entity.setAccess(genericService.find(accessCriteria));
			SearchCriteria<GroupEntity> groupCriteria = new SearchCriteria<>(new GenericFilterDefQueries<>(GroupEntity.class));
			groupCriteria.andFilter(new FilterDefEquals(GroupEntity.CHURCH_A, entity)).andFilter(
					new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
			entity.setGroups(genericService.find(groupCriteria));
		} 
		super.setEntity(entity);
	}

	@Override
	protected ChurchEntity createDefaultEntity() {
		final ChurchEntity entity = new ChurchEntity();
		entity.setOwner(userSessionBean.getSessionUser());
		entity.setCreateDate(new Date());
		final AccessChurchEntity ownerAccess = new AccessChurchEntity();
		ownerAccess.setUser(userSessionBean.getSessionUser());
		ownerAccess.setConfirm(true);
		ownerAccess.setViewPermit(true);
		ownerAccess.setEditPermit(true);
		ownerAccess.setEditUserPermit(true);
		ownerAccess.setAddContactPermit(true);
		ownerAccess.setEditContactPermit(true);
		ownerAccess.setEditAccessPermit(true);
		ownerAccess.setEditGroupPermit(true);
		entity.addAccess(ownerAccess);
		return entity;
	}

	@Override
	protected void afterSaveNavigate() {
		CacheUtils.clearChurchAccessCache();
		navigationHelper.navigate("/admin/church/list.jsf");
	}

	@Override
	protected void cancelNavigate() {
		navigationHelper.navigate("/admin/church/list.jsf");
	}

	public SessionUserEntity getAccessUser() {
		return accessUser;
	}

	public void setAccessUser(SessionUserEntity accessUser) {
		this.accessUser = accessUser;
	}

	public List<AccessChurchEntity> getAccesses() {
		return entity
				.getAccess()
				.stream()
				.filter(access -> {
					return !userSessionBean.getSessionUser().equals(access.getUser())
							&& !entity.getOwner().equals(access.getUser());
				}).sorted((acc1, acc2) -> {
					return acc1.getUser().getLogin().compareToIgnoreCase(acc2.getUser().getLogin());
				}).collect(Collectors.toList());
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
