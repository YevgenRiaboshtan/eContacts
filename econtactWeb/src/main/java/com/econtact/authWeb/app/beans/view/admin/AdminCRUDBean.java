package com.econtact.authWeb.app.beans.view.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

@ManagedBean(name = "adminCRUDBean")
@ViewScoped
public class AdminCRUDBean extends GeneralCRUDBean<AccountUserEntity> {
	private static final long serialVersionUID = 290852894943784355L;

	@Override
	protected boolean canModifyEntity(AccountUserEntity entity) {
		return userSession.getPrincipal().equals(entity.getParentUser());
	}

	@Override
	protected AccountUserEntity createDefaultEntity() {
		AccountUserEntity entity = new AccountUserEntity();
		return entity;
	}

	@Override
	protected void preSave() {
		super.preSave();
		entity.setParentUser(userSession.getPrincipal());
	}
}
