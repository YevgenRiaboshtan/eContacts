package com.econtact.authWeb.app.beans.view.church;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.church.ChurchsDataTableLazyModel;

@ManagedBean (name = "churchTableBean")
@ViewScoped
public class ChurchTableBean extends AbstractViewBean {
	private static final long serialVersionUID = -7179818194774205326L;
	
	private DataTable churchDataTable;
	private ChurchsDataTableLazyModel churchDataTabelModel;
	
	public ChurchsDataTableLazyModel getChurchsDataTabelModel() {
		if (churchDataTabelModel == null) {
			churchDataTabelModel = new ChurchsDataTableLazyModel(churchDataTable, userSessionBean.getUserContext());
		}
		return churchDataTabelModel;
	}

	public void setChurchsDataTabelModel(ChurchsDataTableLazyModel projectsDataTabelModel) {
		this.churchDataTabelModel = projectsDataTabelModel;
	}

	/**
	 * Method to return churchDataTable 
	 * @return the churchDataTable
	 */
	public DataTable getChurchDataTable() {
		return churchDataTable;
	}

	/**
	 * Method to set churchDataTable
	 * @param churchDataTable the churchDataTable to set
	 */
	public void setChurchDataTable(DataTable churchDataTable) {
		this.churchDataTable = churchDataTable;
	}
	
	
}