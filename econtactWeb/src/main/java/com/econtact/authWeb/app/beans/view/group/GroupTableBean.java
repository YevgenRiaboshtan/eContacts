package com.econtact.authWeb.app.beans.view.group;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.church.GroupsDataTableLazyModel;

@ManagedBean (name = "groupTableBean")
@ViewScoped
public class GroupTableBean extends AbstractViewBean {
	private static final long serialVersionUID = -2368142132304728771L;

	private GroupsDataTableLazyModel groupModel;

	public GroupsDataTableLazyModel getGroupModel() {
		if (groupModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("groupTableForm:groupDataTable");
			groupModel = new GroupsDataTableLazyModel(table, userSessionBean.getUserContext());
		}
		return groupModel;
	}

	public void setGroupModel(GroupsDataTableLazyModel groupModel) {
		this.groupModel = groupModel;
	}
}
