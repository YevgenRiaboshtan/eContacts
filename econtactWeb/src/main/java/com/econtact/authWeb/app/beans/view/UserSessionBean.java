package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import com.econtact.dataModel.model.entity.AbstractEntity;

@ManagedBean
@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 5815150040159902787L;
	
	private AbstractEntity editedObject;

	public AbstractEntity getEditedObject() {
		return editedObject;
	}

	public void setEditedObject(AbstractEntity editedObject) {
		this.editedObject = editedObject;
	}
	
	@PostConstruct
	public void init() {
		System.out.println("usersession init");
	}
	
	
}
