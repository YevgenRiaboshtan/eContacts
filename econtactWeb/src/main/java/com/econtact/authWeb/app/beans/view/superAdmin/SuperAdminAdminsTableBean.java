package com.econtact.authWeb.app.beans.view.superAdmin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.econtact.authWeb.app.beans.view.GeneralTableBean;
import com.econtact.authWeb.app.dataTable.model.superAdmin.AdminDataTableLazyModel;
import com.econtact.authWeb.app.dataTable.model.superAdmin.UserHistoryDataTableLazyModel;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

@ManagedBean(name = "superAdminAdminsTableBean")
@ViewScoped
public class SuperAdminAdminsTableBean extends GeneralTableBean {
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

	public void showUserHistory(AccountUserEntity user) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        Map<String, List<String>> param = new HashMap<String, List<String>>();
        param.put("idUser", new ArrayList<>(Arrays.asList(user.getId().toString())));
		RequestContext.getCurrentInstance().openDialog("users/userHistory", options, param);
	}
}
