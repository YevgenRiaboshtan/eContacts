package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.superAdmin.AdminDataTableLazyModel;

@ManagedBean(name = "superAdminAdminsTableBean")
@ViewScoped
public class SuperAdminAdminsTableBean extends AbstractViewBean {
	private static final long serialVersionUID = -7422685704892233421L;

	private DataTable dataTable;
	
	private AdminDataTableLazyModel adminsModel;

	public AdminDataTableLazyModel getAdminsModel() {
		if (adminsModel == null) {
			adminsModel = new AdminDataTableLazyModel(dataTable, userSessionBean.getUserContext());
		}
		return adminsModel;
	}

	public void setAdminsModel(AdminDataTableLazyModel adminsModel) {
		this.adminsModel = adminsModel;
	}

	/**
	 * Method to return dataTable 
	 * @return the dataTable
	 */
	public DataTable getDataTable() {
		return dataTable;
	}

	/**
	 * Method to set dataTable
	 * @param dataTable the dataTable to set
	 */
	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
}
