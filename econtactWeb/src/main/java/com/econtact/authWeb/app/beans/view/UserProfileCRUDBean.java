package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

@ManagedBean(name = "userProfileCRUDBean")
@ViewScoped
public class UserProfileCRUDBean extends AbstractViewBean implements Serializable {
	private static final long serialVersionUID = 843495965643883228L;
	
	@EJB
	GenericService genericService;

	private AccountUserEntity entity;

	private boolean editable = false;
	
	private String newPassword;

	@PostConstruct
	public void init() {
		entity = genericService.findById(AccountUserEntity.class, userSessionBean.getSessionUser().getId());
	}

	public AccountUserEntity getEntity() {
		return entity;
	}

	public void save() throws UniqueConstraintException {	
		if (StringUtils.isNotBlank(newPassword)) {
			entity.setPassword(PasswordUtils.convertPassword(newPassword, entity.getSalt()));
		}
		entity = genericService.saveOrUpdate(entity, userSessionBean.getUserContext());
		editable = false;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean isEditable() {
		return editable;
	}
	
	public void edit() {
		editable = true;
	}
}
