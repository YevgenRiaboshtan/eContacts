package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@ManagedBean(name = "userProfileCRUDBean")
@ViewScoped
public class UserProfileCRUDBean implements Serializable {
	private static final long serialVersionUID = 843495965643883228L;

	@Inject
	UserSessionBean userSessionBean;
	
	@EJB
	GenericService genericService;
	
	private AccountUserEntity entity;
	
	private String newFirstName;
	private String newLastName;
	
	@PostConstruct
	public void init() {
		entity = genericService.findById(AccountUserEntity.class, userSessionBean.getPrincipal().getId());
	}

	public AccountUserEntity getEntity() {
		return entity;
	}
	
	public void save() throws UniqueConstraintException {
		if (StringUtils.isNotBlank(newFirstName)) {
			entity.setFirstName(newFirstName);
		}
		if (StringUtils.isNotBlank(newLastName)) {
			entity.setLastName(newLastName);
		}
		entity = genericService.saveOrUpdate(entity, userSessionBean.getUserContext());
		((SessionUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setFirstName(entity.getFirstName());
		((SessionUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setLastName(entity.getLastName());
		userSessionBean.init();
		newFirstName = "";
		newLastName = "";
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
}
