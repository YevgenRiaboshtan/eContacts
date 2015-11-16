package com.econtact.authWeb.app.beans.view.superAdmin;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.superAdmin.AdminDataTableLazyModel;
import com.econtact.authWeb.app.dataTable.model.superAdmin.ConnectLogDataTableLazyModel;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;

@ManagedBean(name = "superAdminBean")
@ViewScoped
public class SuperAdminBean extends AbstractViewBean<AccountUserEntity> {
	private static final long serialVersionUID = 7945612989043879384L;
	
	private AdminDataTableLazyModel dataModel;
	
	private ConnectLogDataTableLazyModel connectLogModel;

	private String newPassword;
 
	@Override
	protected void preSave() {
		if (StringUtils.isEmpty(entity.getSalt())) {
			entity.setSalt(PasswordUtils.getRandomSalt());
			entity.setPassword(PasswordUtils.convertPassword(entity.getPassword(), entity.getSalt()));
		}
		if (StringUtils.isNotBlank(newPassword)) {
			entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
		}
		entity.setLogin(entity.getLogin().toLowerCase());
		entity.setRole(RoleType.ROLE_ADMIN);
		entity.setRoleConfirm(ConfirmStatusEnum.CONFIRMED);
		entity.setAllowCreateRegister(true);
		entity.setParentUser(WebHelper.getPrincipal());
	}
		
	public AdminDataTableLazyModel getDataModel() {
		if (dataModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("adminTableForm:adminDataTable");
			dataModel = new AdminDataTableLazyModel(table, getFilterHelper());
		}
		return dataModel;
	}

	public void setDataModel(AdminDataTableLazyModel dataModel) {
		this.dataModel = dataModel;
	}

	public ConnectLogDataTableLazyModel getConnectLogModel() {
		if (connectLogModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("connectLogForm:connectLogDataTable");
			connectLogModel = new ConnectLogDataTableLazyModel(table, getFilterHelper());
		}
		return connectLogModel;
	}

	public void setConnectLogModel(ConnectLogDataTableLazyModel connectLogModel) {
		this.connectLogModel = connectLogModel;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void editSelectedUser(AccountUserEntity user) throws IOException{
		userSessionBean.setEditedObject(user);
		navigationHelper.navigate("edit.jsf");
	}
	
	@Override
	protected AccountUserEntity createDefaultEntity() {
		return new AccountUserEntity();
	}
}
