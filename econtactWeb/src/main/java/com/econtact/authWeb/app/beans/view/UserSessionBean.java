package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.menu.MenuModel;

import com.econtact.authWeb.app.utils.MenuUtils;
import com.econtact.dataModel.model.entity.AbstractEntity;


@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 5815150040159902787L;
	
	@Inject
	private MenuUtils menuUtils;
	
	private MenuModel topMenuModel;
	
	private AbstractEntity editedObject;

	public AbstractEntity getEditedObject() {
		return editedObject;
	}

	public void setEditedObject(AbstractEntity editedObject) {
		System.out.println( "user session beanr " + this.editedObject);
		this.editedObject = editedObject;
		System.out.println("user session edited object " + this.toString());
		if (editedObject != null) {
			System.out.println("user session edited object - " + editedObject.getClass() + " - " + editedObject.getId());
		} else {
			System.out.println("user session edited object - null");
		}
	}
	
	@PostConstruct
	public void init() {
		System.out.println("usersession init");
	}

	public MenuModel getTopMenuModel() {
		if (topMenuModel == null) {
			topMenuModel = menuUtils.buildTopMenu();
		}
		return topMenuModel;
	}
}
