package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * Abstract Bean class.
 * Абстрактный класс для Bean комопнент.
 * @author Yevgen Riaboshtan
 *
 */
public abstract class AbstractViewBean implements Serializable {
	private static final long serialVersionUID = 5491413519461094802L;
	
	/**
	 * User session instance. {@link UserSessionBean}
	 * Экземпляр сессионного бина пользователя {@link UserSessionBean}
	 */
	@Inject
	protected UserSessionBean userSessionBean;
}
