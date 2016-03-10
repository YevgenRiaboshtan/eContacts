package com.econtact.authWeb.app.beans.view.superAdmin;

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


@ManagedBean(name = "superAdminCRUDBean")
@ViewScoped
public class SuperAdminCRUDBean extends GeneralCRUDBean<AccountUserEntity> {
	private static final long serialVersionUID = -4711284993165586144L;

	private String newPassword;
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	@Override
	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate("/superAdmin/admins/list.jsf");
	}
	
	@Override
	protected void cancelNavigate() throws IOException {
		navigationHelper.navigate("/superAdmin/admins/list.jsf");
	}
	
	@Override
	protected void preSave() {
		if (entity.getId() == null) {
			entity.setPassword(PasswordUtils.convertPassword(entity.getPassword(), entity.getSalt()));
		}
		if (StringUtils.isNotBlank(newPassword)) {
			entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
		}
		entity.setLogin(entity.getLogin().toLowerCase());
	}

	@Override
	protected AccountUserEntity createDefaultEntity() {
		AccountUserEntity result = new AccountUserEntity();
		result.setRole(RoleType.ROLE_ADMIN);
		result.setRoleConfirm(ConfirmStatusEnum.CONFIRMED);
		result.setAllowCreateRegister(true);
		result.setParentUser(userSessionBean.getPrincipal());
		result.setEnabledUser(UserStatusEnum.ENABLE);
		result.setSalt(PasswordUtils.getRandomSalt());
		return result;
	}
	
	protected boolean canModifyEntity(AccountUserEntity entity){
		return true;
	}
}
