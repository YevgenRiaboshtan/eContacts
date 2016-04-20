package com.econtact.authWeb.app.beans.view.contact;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.dataModel.model.entity.contact.PersonEntity;

@ManagedBean(name = "addContactBean")
@ViewScoped
public class AddContactBean extends GeneralCRUDBean<PersonEntity> {
	private static final long serialVersionUID = -3735515283213292556L;

	@Override
	protected boolean canModifyEntity(PersonEntity entity) {
		if (entity.getId() != null) {
			throw new UnsupportedOperationException("Edit person not supported");
		}
		return true;
	}
	
	@Override
	protected void preSave() {
		setEntity(null);
	}

	@Override
	protected PersonEntity createDefaultEntity() {
		PersonEntity entity = new PersonEntity();
		entity.setChurch(userSessionBean.getCurrentChurch());
		return entity;
	}

	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	public void saveAndCreate() throws IOException {
		save();
	}
	
	public void saveAndExit() throws IOException {
		save();
		super.afterSaveNavigate();
	}
}
