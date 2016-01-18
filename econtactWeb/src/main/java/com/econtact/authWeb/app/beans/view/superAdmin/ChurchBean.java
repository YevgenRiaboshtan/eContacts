package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.church.ChurchsDataTableLazyModel;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@ManagedBean (name = "churchBean")
@ViewScoped
public class ChurchBean extends AbstractViewBean<ChurchEntity> {
	private static final long serialVersionUID = -7179818194774205326L;

	private ChurchsDataTableLazyModel churchDataTabelModel;
	
	@Override
	protected ChurchEntity createDefaultEntity() {
		ChurchEntity result = new ChurchEntity();
		result.setOwner(WebHelper.getPrincipal());
		if (RoleType.ROLE_ADMIN.equals(WebHelper.getPrincipal().getRole())) {
			result.setConfirmCreate(ConfirmStatusEnum.CONFIRMED);
		} else {
			result.setConfirmCreate(ConfirmStatusEnum.NOT_CONFIRMED);
		}
		return result;
	}

	public ChurchsDataTableLazyModel getChurchsDataTabelModel() {
		if (churchDataTabelModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("projectTableForm:projectDataTable");
			churchDataTabelModel = new ChurchsDataTableLazyModel(table, getFilterHelper());
		}
		return churchDataTabelModel;
	}

	public void setChurchsDataTabelModel(ChurchsDataTableLazyModel projectsDataTabelModel) {
		this.churchDataTabelModel = projectsDataTabelModel;
	}

	@Override
	protected String getEditObjectPage() {
		return "/user/church/editChurch.xhtml";
	}
}