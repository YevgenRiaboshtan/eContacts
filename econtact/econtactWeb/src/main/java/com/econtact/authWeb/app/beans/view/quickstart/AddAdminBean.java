package com.econtact.authWeb.app.beans.view.quickstart;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.quickStart.AdminDataTableLazyModel;
import com.econtact.authWeb.app.helpers.NavigationHelper;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.authWeb.app.utils.ContraintViewRelation;
import com.econtact.authWeb.app.utils.UniqueConstraintHandleUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccessStatusEnum;
import com.econtact.dataModel.model.entity.accout.ActiveStatusEnum;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserEntity;
	
@ManagedBean(name = "addAdminBean")
@ViewScoped
public class AddAdminBean implements Serializable{
	private static final long serialVersionUID = 7945612989043879384L;
	
	@Inject
	NavigationHelper  navigationHelper;
	
	@EJB
	private GenericService genericService;

	private AdminDataTableLazyModel adminListModel;
	
	private AdvanceUserEntity entity;
	
	private UserEntity selectedUser;

	public AdvanceUserEntity getEntity() {
		return entity;
	}

	public void setEntity(AdvanceUserEntity entity) {
		this.entity = entity;
	}	
	
	public void save() throws IOException {
		entity.setSalt(PasswordUtils.getRandomSalt());
		entity.setPassword(PasswordUtils.convertPassword(entity.getPassword(), entity.getSalt()));
		entity.addRole(RoleType.ROLE_ADMIN, AccessStatusEnum.CONFIRMED);
		entity.setEnabledAccount(ActiveStatusEnum.ENABLE);
		try{
			entity = WebHelper.getBean(GenericService.class).saveOrUpdate(entity, WebHelper.getUserContext());
			navigationHelper.navigate(navigationHelper.getListPage());
		} catch (UniqueConstraintException e) {
			ContraintViewRelation relation = UniqueConstraintHandleUtils.getInstance().handleException(e);
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "viewMsg");
			
			FacesContext.getCurrentInstance().addMessage(relation.getIdField(), new FacesMessage(bundle.getString(relation.getErrorMessageKey())));
		}
		
		
	}
	
	@PostConstruct
	public void init() {
		if (StringUtils.isNotBlank(getParameter(NavigationHelper.ID_PARAM))){
			BigDecimal id = new BigDecimal(getParameter(NavigationHelper.ID_PARAM)); 
			entity = WebHelper.getBean(GenericService.class).findById(AdvanceUserEntity.class, id);
		} else {
			entity = new AdvanceUserEntity();
		}
	}

	public AdminDataTableLazyModel getAdminListModel() {
		if (adminListModel == null ) {
			DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("adminTableForm:adminDataTable");
			adminListModel = new AdminDataTableLazyModel(table);
		}
		return adminListModel;
	}

	public void setAdminListModel(AdminDataTableLazyModel adminListModel) {
		this.adminListModel = adminListModel;
	}

	public UserEntity getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserEntity selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	protected String getParameter(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(key);
	}
}
