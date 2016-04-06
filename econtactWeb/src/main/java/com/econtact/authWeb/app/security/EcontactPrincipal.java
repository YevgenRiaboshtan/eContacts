package com.econtact.authWeb.app.security;

import java.io.Serializable;
import java.security.Principal;

import javax.validation.constraints.NotNull;

import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public class EcontactPrincipal implements Principal, Serializable{
	private static final long serialVersionUID = -323490545353222491L;

	private final SessionUserEntity userAccount;
	
	public EcontactPrincipal(@NotNull SessionUserEntity user) {
		this.userAccount = user;
	}
	
	@Override
	public String getName() {
		return userAccount.getLogin();
	}

}
