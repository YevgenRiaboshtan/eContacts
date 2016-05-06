package com.econtact.authWeb.app.beans.view.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.econtact.authWeb.app.beans.helper.FilterHelper;
import com.econtact.authWeb.app.beans.view.superAdmin.SuperAdminCRUDBean;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

@ManagedBean(name = "adminCRUDBean")
@ViewScoped
public class AdminCRUDBean extends SuperAdminCRUDBean {
	private static final long serialVersionUID = 290852894943784355L;

	@Inject
	private FilterHelper filterHelper;
	
	@Override
	protected boolean canModifyEntity(AccountUserEntity entity) {
		return userSessionBean.getSessionUser().equals(entity.getParentUser()) && super.canModifyEntity(entity);
	}

	@Override
	protected AccountUserEntity createDefaultEntity() {
		AccountUserEntity entity = new AccountUserEntity();
		entity.setRole(RoleType.ROLE_EMPLOYEE);
		entity.setRoleConfirm(ConfirmStatusEnum.CONFIRMED);
		entity.setSalt(PasswordUtils.getRandomSalt());
		entity.setParentUser(userSessionBean.getSessionUser());
		entity.setEnabledUser(UserStatusEnum.ENABLE);
		return entity;
	}
	
	public List<SelectItem> getRoleTypes() throws ClassNotFoundException {
		return filterHelper.getEnumSelectItems(RoleType.class.getName(), false).stream().filter(item -> {
			return (RoleType.ROLE_EMPLOYEE.equals(item.getValue())
					|| RoleType.ROLE_REGISTER.equals(item.getValue()))
					? true
					: false;
		}).collect(Collectors.toList());
	}
}
