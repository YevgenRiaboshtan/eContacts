package com.econtact.authWeb.app.beans.view.contact;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.inputtext.InputText;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.entity.contact.AddressEntity;
import com.econtact.dataModel.model.entity.contact.ContactEntity;
import com.econtact.dataModel.model.entity.contact.PersonEntity;

@ManagedBean(name = "addContactBean")
@ViewScoped
public class AddContactBean extends GeneralCRUDBean<PersonEntity> {
	private static final long serialVersionUID = -3735515283213292556L;
	
	private ContactEntity addingContact = new ContactEntity();
	
	@Override
	protected boolean canModifyEntity(PersonEntity entity) {
		if (entity.getId() != null) {
			throw new UnsupportedOperationException("Edit person not supported");
		}
		return true;
	}
	
	@Override
	protected void preSave() {
		if (getEntity().getAddress().isEmpty()) {
			getEntity().setAddress(null);
		}
		setEntity(null);
	}

	@Override
	protected PersonEntity createDefaultEntity() {
		PersonEntity entity = new PersonEntity();
		entity.setAddress(new AddressEntity());
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
	
	public void addContact() {
		if (addingContact != null) {
			getEntity().addContact(addingContact);
			addingContact = new ContactEntity();
		}
	}
	
	/**
	 * Method to return addingContact 
	 * @return the addingContact
	 */
	public ContactEntity getAddingContact() {
		return addingContact;
	}

	/**
	 * Method to set addingContact
	 * @param addingContact the addingContact to set
	 */
	public void setAddingContact(ContactEntity addingContact) {
		this.addingContact = addingContact;
	}	
}
