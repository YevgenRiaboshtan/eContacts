package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.superAdmin.AdminDataTableLazyModel;

@ManagedBean(name = "superAdminAdminsTableBean")
@ViewScoped
public class SuperAdminAdminsTableBean extends AbstractViewBean {
	private static final long serialVersionUID = -7422685704892233421L;

	private AdminDataTableLazyModel adminsModel;

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
}
