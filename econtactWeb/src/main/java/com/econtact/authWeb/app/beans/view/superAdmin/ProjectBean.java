package com.econtact.authWeb.app.beans.view.superAdmin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.dataTable.model.project.ProjectsDataTableLazyModel;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.project.ProjectEntity;

@ManagedBean (name = "projectBean")
@ViewScoped
public class ProjectBean extends AbstractViewBean<ProjectEntity> {
	private static final long serialVersionUID = -7179818194774205326L;

	private ProjectsDataTableLazyModel projectsDataTabelModel;
	
	@Override
	protected ProjectEntity createDefaultEntity() {
		ProjectEntity result = new ProjectEntity();
		result.setOwner(WebHelper.getPrincipal());
		if (RoleType.ROLE_ADMIN.equals(WebHelper.getPrincipal().getRole())) {
			result.setConfirmCreate(ConfirmStatusEnum.CONFIRMED);
		} else {
			result.setConfirmCreate(ConfirmStatusEnum.NOT_CONFIRMED);
		}
		return result;
	}

	public ProjectsDataTableLazyModel getProjectsDataTabelModel() {
		if (projectsDataTabelModel == null) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent("projectTableForm:projectDataTable");
			projectsDataTabelModel = new ProjectsDataTableLazyModel(table, getFilterHelper());
		}
		return projectsDataTabelModel;
	}

	public void setProjectsDataTabelModel(ProjectsDataTableLazyModel projectsDataTabelModel) {
		this.projectsDataTabelModel = projectsDataTabelModel;
	}

	@Override
	protected String getEditObjectPage() {
		return "/user/project/editProject.xhtml";
	}
}