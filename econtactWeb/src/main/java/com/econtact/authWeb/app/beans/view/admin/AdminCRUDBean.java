package com.econtact.authWeb.app.beans.view.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;

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
		return entity;
	}

	@Override
	protected void preSave() {
		super.preSave();
		if (StringUtils.isBlank(entity.getPassword())) {
			entity.setPassword(PasswordUtils.convertPassword(entity.getPassword(), entity.getSalt()));
		}
		if (StringUtils.isNotBlank(newPassword)) {
			entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
		}
		entity.setParentUser(userSession.getPrincipal());
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
}
