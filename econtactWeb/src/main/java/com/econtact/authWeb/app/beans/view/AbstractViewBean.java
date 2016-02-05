package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.inject.Inject;

public abstract class AbstractViewBean implements Serializable {
	private static final long serialVersionUID = 5491413519461094802L;
	
	@Inject
	protected UserSessionBean userSessionBean;
	
	
}
