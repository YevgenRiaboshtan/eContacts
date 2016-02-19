package com.econtact.authWeb.app.beans.view.admin;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

@ManagedBean(name = "adminCRUDBean")
@ViewScoped
public class AdminCRUDBean extends GeneralCRUDBean<AccountUserEntity> {
	private static final long serialVersionUID = 290852894943784355L;

	private String newPassword;
	
	@Override
	protected boolean canModifyEntity(AccountUserEntity entity) {
		return userSession.getPrincipal().equals(entity.getParentUser());
	}

	@Override
	protected AccountUserEntity createDefaultEntity() {
		AccountUserEntity entity = new AccountUserEntity();
		entity.setRole(RoleType.ROLE_EMPLOYEE);
		entity.setRoleConfirm(ConfirmStatusEnum.CONFIRMED);
		entity.setSalt(PasswordUtils.getRandomSalt());
		entity.setParentUser(userSession.getPrincipal());
		entity.setEnabledUser(UserStatusEnum.ENABLE);
		return entity;
	}

	@Override
	protected void preSave() {
		super.preSave();
		if (entity.getId() == null) {
			entity.setPassword(PasswordUtils.convertPassword(entity.getPassword(), entity.getSalt()));
		}
		if (StringUtils.isNotBlank(newPassword)) {
			entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
		}
		entity.setLogin(entity.getLogin().toLowerCase());
	}
	
	protected String getDefaultEntityGraph() {
		return AccountUserEntity.ACCOUNT_PARENT_GRAPH;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate("/admin/employee/list.jsf");
	}
	
	protected void cancelNavigate() throws IOException {
		navigationHelper.navigate("/admin/employee/list.jsf");
	}
}
