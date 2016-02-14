package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.authWeb.app.beans.helper.LabelsHelper;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@ManagedBean(name = "userProfileCRUDBean")
@ViewScoped
public class UserProfileCRUDBean implements Serializable {

	private static final long serialVersionUID = 843495965643883228L;

	@Inject
	LabelsHelper labelsHelper;
	
	@Inject
	UserSessionBean userSessionBean;

	@EJB
	GenericService genericService;

	private AccountUserEntity entity;

	private boolean editable = false;
	
	
	private String newFirstName;
	private String newLastName;
	private String currentPassword;
	private String newPassword;
	private String confirmNewPassword;

	@PostConstruct
	public void init() {
		entity = genericService.findById(AccountUserEntity.class, userSessionBean.getPrincipal().getId());
	}

	public AccountUserEntity getEntity() {
		return entity;
	}

	public void save() throws UniqueConstraintException {
		boolean needInit = false;
		if (StringUtils.isNotBlank(newFirstName)) {
			entity.setFirstName(newFirstName);
			needInit = true;
		}
		if (StringUtils.isNotBlank(newLastName)) {
			entity.setLastName(newLastName);
			needInit = true;
		}
		if (StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(currentPassword)
				&& StringUtils.isNotBlank(confirmNewPassword)) {
			if (PasswordUtils.machPassword(entity.getPassword(), currentPassword, entity.getSalt())) {
				entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
			} else {
				FacesContext.getCurrentInstance().addMessage("currentPassword",
						new FacesMessage(labelsHelper.getLocalizedMessage(LocaleLabels.NEW_USER_WRONG_CURRENT_PASSWORD_ERROR_MESSAGE)));
				currentPassword = "";
				newPassword = "";
				confirmNewPassword = "";
				return;
			}
		}
		
		entity = genericService.saveOrUpdate(entity, userSessionBean.getUserContext());

		if (needInit) {
			((SessionUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.setFirstName(entity.getFirstName());
			((SessionUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.setLastName(entity.getLastName());
			userSessionBean.init();
			newFirstName = "";
			newLastName = "";
		}
		
		confirmNewPassword = "";
		newPassword = "";
		currentPassword = "";
	}

	public String getNewFirstName() {
		return newFirstName;
	}

	public void setNewFirstName(String newFirstName) {
		this.newFirstName = newFirstName;
	}

	public String getNewLastName() {
		return newLastName;
	}

	public void setNewLastName(String newLastName) {
		this.newLastName = newLastName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	
}
