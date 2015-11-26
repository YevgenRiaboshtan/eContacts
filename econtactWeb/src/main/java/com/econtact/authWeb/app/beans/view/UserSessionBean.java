package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import org.primefaces.model.menu.MenuModel;

import com.econtact.authWeb.app.utils.MenuUtils;
import com.econtact.dataModel.model.entity.AbstractEntity;


@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 5815150040159902787L;
	
	private MenuModel topMenuModel;
	
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

	public MenuModel getTopMenuModel() {
		if (topMenuModel == null) {
			topMenuModel = MenuUtils.buildTopMenu();
		}
		return topMenuModel;
	}
}
