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

import com.econtact.authWeb.app.dataTable.model.quickStart.AdminDataTableLazyModel;
import com.econtact.authWeb.app.helpers.NavigationHelper;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.authWeb.app.utils.ContraintViewRelation;
import com.econtact.authWeb.app.utils.UniqueConstraintHandleUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccessStatusEnum;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;
	
@ManagedBean(name = "addAdminBean")
@ViewScoped
public class AddAdminBean implements Serializable{
	private static final long serialVersionUID = 7945612989043879384L;
	
	@Inject
	NavigationHelper  navigationHelper;
	
	@EJB
	private GenericService genericService;
	
	private AdminDataTableLazyModel dataModel;
	
	private AdvanceUserEntity entity;
	private String newPassword;
	
	public AdvanceUserEntity getEntity() {
		return entity;
	}

	public void setEntity(AdvanceUserEntity entity) {
		this.entity = entity;
	}	
	
	public void remove(AdvanceUserEntity user) {
		WebHelper.getBean(GenericService.class).remove(user, WebHelper.getUserContext());
	}
	
	public void saveEntity() throws IOException {
		if (StringUtils.isEmpty(entity.getSalt())) {
			entity.setSalt(PasswordUtils.getRandomSalt());
		}
		if (StringUtils.isNotBlank(newPassword)) {
			entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
		}
		entity.addRole(RoleType.ROLE_ADMIN, AccessStatusEnum.CONFIRMED);
		navigationHelper.navigate(navigationHelper.getListPage());			
		try{
			entity = WebHelper.getBean(GenericService.class).saveOrUpdate(entity, WebHelper.getUserContext());
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
			setEntity(WebHelper.getBean(GenericService.class).findById(AdvanceUserEntity.class, id));
		} else {
			setEntity(createDefaultEntity());
		}
	}

	protected String getParameter(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(key);
	}
	
	public AdvanceUserEntity createDefaultEntity() {
		return new AdvanceUserEntity();
	}

	public AdminDataTableLazyModel getDataModel() {
		if (dataModel == null) {
			dataModel = new AdminDataTableLazyModel();
		}
		return dataModel;
	}

	public void setDataModel(AdminDataTableLazyModel dataModel) {
		this.dataModel = dataModel;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}	
}
