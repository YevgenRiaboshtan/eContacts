package com.econtact.authWeb.app.beans.view.quickstart;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.quickStart.AdminDataTableLazyModel;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;

@ManagedBean(name = "addAdminBean")
@ViewScoped
public class AddAdminBean extends AbstractViewBean<AccountUserEntity> {
	private static final long serialVersionUID = 7945612989043879384L;
	
	private AdminDataTableLazyModel dataModel;

	private String newPassword;
 
	@Override
	protected void preSave() {
		if (StringUtils.isEmpty(entity.getSalt())) {
			entity.setSalt(PasswordUtils.getRandomSalt());
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
			dataModel = new AdminDataTableLazyModel(table, filterHelper);
		}
		return dataModel;
	}

	public void setDataModel(AdminDataTableLazyModel dataModel) {
		this.dataModel = dataModel;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	protected AccountUserEntity createDefaultEntity() {
		return new AccountUserEntity();
	}
}
