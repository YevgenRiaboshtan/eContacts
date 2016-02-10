package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.superAdmin.AdminDataTableLazyModel;
import com.econtact.authWeb.app.dataTable.model.superAdmin.UserHistoryDataTableLazyModel;

@ManagedBean(name = "superAdminAdminsTableBean")
@ViewScoped
public class SuperAdminAdminsTableBean extends AbstractViewBean {
	private static final long serialVersionUID = -7422685704892233421L;

	private AdminDataTableLazyModel adminsModel;

	private UserHistoryDataTableLazyModel adminHistoryModel;

	public AdminDataTableLazyModel getAdminsModel() {
		if (adminsModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("adminTableForm:adminDataTable");
			adminsModel = new AdminDataTableLazyModel(table, userSessionBean.getUserContext());
		}
		return adminsModel;
	}

	public void setAdminsModel(AdminDataTableLazyModel adminsModel) {
		this.adminsModel = adminsModel;
	}

	public UserHistoryDataTableLazyModel getAdminHistoryModel(String idUser) {
		if (adminHistoryModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("userHistoryForm:userHistoryDatatable");
			adminHistoryModel = new UserHistoryDataTableLazyModel(table, userSessionBean.getUserContext(), idUser);
		}
		return adminHistoryModel;
	}

	public void setAdminHistoryModel(UserHistoryDataTableLazyModel adminHistoryModel) {
		this.adminHistoryModel = adminHistoryModel;
	}
}
