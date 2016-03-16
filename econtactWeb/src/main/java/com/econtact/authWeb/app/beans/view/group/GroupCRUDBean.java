package com.econtact.authWeb.app.beans.view.group;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.access.AccessGroupEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

@ManagedBean(name = "groupCRUDBean")
@ViewScoped
public class GroupCRUDBean extends GeneralCRUDBean<GroupEntity> {
	private static final long serialVersionUID = -3648293594278396865L;
	
	private List<ChurchEntity> churchs;
	
	@Override
	protected boolean canModifyEntity(GroupEntity entity) {
		if (EntityHelper.ACTUAL_SIGN.equals(entity.getSign())
				&& userSessionBean.getGroupAccess(entity.getId()) != null
				&& userSessionBean.getGroupAccess(entity.getId()).isEditPermit()) {
			return true;
		}
		return false;
	}

	@Override
	protected void preSave() {
		if (entity.getAccesses().stream().noneMatch(item -> entity.getChurch().getOwner().equals(item.getUser()))) {
			final AccessGroupEntity ownerAccess = new AccessGroupEntity();
			ownerAccess.setUser(entity.getChurch().getOwner());
			ownerAccess.setConfirm(true);
			ownerAccess.setEditPermit(true);
			ownerAccess.setRegisterPermit(true);
			entity.addAccess(ownerAccess);
		}
	}
	
	@Override
	protected GroupEntity createDefaultEntity() {
		final GroupEntity entity = new GroupEntity();
		final AccessGroupEntity access = new AccessGroupEntity();
		access.setUser(userSessionBean.getPrincipal());
		access.setConfirm(true);
		access.setEditPermit(true);
		access.setViewPermit(true);
		access.setRegisterPermit(true);
		entity.addAccess(access);
		return entity;
	}
	
	@Override
	protected String getDefaultEntityGraph() {
		return GroupEntity.GROUP_ACCESSES_GRAPH;
	}

	public List<ChurchEntity> getChurch() {
		if (churchs == null) {
			SearchCriteria<ChurchEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(ChurchEntity.class));
			criteria.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN))
					.andFilter(new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN))
					.andFilter(new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + AccessChurchEntity.CONFIRM_A, true))
					.andFilter(new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + AccessChurchEntity.EDIT_GROUP_PERMIT_A, true))
					.andFilter(new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + AccessChurchEntity.USER_A, userSessionBean.getPrincipal()));
			churchs = genericService.find(criteria);
		}
		return churchs;
	}
	
	public List<AccessGroupEntity> getAccess() {
		return entity.getAccesses().stream().filter(item -> {
			return !item.getUser().equals(userSessionBean.getPrincipal())
					&& !item.getUser().equals(item.getGroup().getChurch().getOwner());
		}).collect(Collectors.toList());
	}
}
