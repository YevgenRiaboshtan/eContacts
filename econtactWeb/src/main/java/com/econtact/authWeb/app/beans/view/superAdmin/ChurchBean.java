package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.GeneralTableBean;
import com.econtact.authWeb.app.dataTable.model.church.ChurchsDataTableLazyModel;

@ManagedBean (name = "churchTableBean")
@ViewScoped
public class ChurchBean extends GeneralTableBean {
	private static final long serialVersionUID = -7179818194774205326L;

	private ChurchsDataTableLazyModel churchDataTabelModel;
	
	public ChurchsDataTableLazyModel getChurchsDataTabelModel() {
		if (churchDataTabelModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("projectTableForm:projectDataTable");
			churchDataTabelModel = new ChurchsDataTableLazyModel(table, userSessionBean.getUserContext());
		}
		return churchDataTabelModel;
	}

	public void setChurchsDataTabelModel(ChurchsDataTableLazyModel projectsDataTabelModel) {
		this.churchDataTabelModel = projectsDataTabelModel;
	}
}