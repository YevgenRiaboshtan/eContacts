package com.econtact.authWeb.app.beans.view.employee;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.employee.EmployeesTableLazyModel;

@ManagedBean(name = "employeesTableBean")
@ViewScoped
public class EmployeesTableBean extends AbstractViewBean {
	private static final long serialVersionUID = 674867730468306771L;

	private EmployeesTableLazyModel adminEmployeeTableModel;

	public EmployeesTableLazyModel getAdminEmployeeTableModel() {
		if (adminEmployeeTableModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("employeeTableForm:employeeDataTable");
			adminEmployeeTableModel = new EmployeesTableLazyModel(table, userSessionBean.getUserContext());
		}
		return adminEmployeeTableModel;
	}

	public void setAdminEmployeeTableModel(EmployeesTableLazyModel adminEmployeeTableModel) {
		this.adminEmployeeTableModel = adminEmployeeTableModel;
	}
	
	
}
