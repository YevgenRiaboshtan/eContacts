package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

@ManagedBean(name = "userProfileCRUDBean")
@ViewScoped
public class UserProfileCRUDBean implements Serializable {
	private static final long serialVersionUID = -1117175058938629303L;

	@Inject
	UserSessionBean userSessionBean;
	
	@EJB
	GenericService genericService;
	
	private AccountUserEntity entity;
	
	@PostConstruct
	public void init() {
		entity = genericService.findById(AccountUserEntity.class, userSessionBean.getPrincipal().getId());
	}

	public AccountUserEntity getEntity() {
		return entity;
	}
	
	public void save() {
		
	}

}
