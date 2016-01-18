package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.GeneralTableBean;
import com.econtact.authWeb.app.dataTable.model.superAdmin.ConnectLogDataTableLazyModel;

@ManagedBean(name = "superAdminConnectLogTableBean")
@ViewScoped
public class SuperAdminConnectLogTableBean extends GeneralTableBean {
	private static final long serialVersionUID = 9003339582411814740L;

	private ConnectLogDataTableLazyModel connectLogModel;
	
	public ConnectLogDataTableLazyModel getConnectLogModel() {
		if (connectLogModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("connectLogForm:connectLogDataTable");
			connectLogModel = new ConnectLogDataTableLazyModel(table, userSessionBean.getUserContext());
		}
		return connectLogModel;
	}

	public void setConnectLogModel(ConnectLogDataTableLazyModel connectLogModel) {
		this.connectLogModel = connectLogModel;
	}
}
