package com.econtact.authWeb.app.beans.view;

import java.io.Serializable;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.MenuModel;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.authWeb.app.beans.helper.MenuHelper;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Named
@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 5815150040159902787L;
	
	@EJB
	private AuthenticationService authenticationService;
	
	private SessionUserEntity principal;
	private UserContext userContext;
	
	@Inject
	private MenuHelper menuUtils;
	
	private MenuModel topMenuModel;
	
	@PostConstruct
	public void init() {
		principal = (SessionUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userContext = UserContext.create(getPrincipal(), TimeZone.getTimeZone("GMT+2"));
	}

	@PreDestroy
	public void destroy() {
		authenticationService.disconnectUser("", principal);
	}
	
	public MenuModel getTopMenuModel() {
		if (topMenuModel == null) {
			topMenuModel = menuUtils.buildTopMenu(getPrincipal().getRole());
		}
		return topMenuModel;
	}

	public SessionUserEntity getPrincipal() {
		return principal;
	}
	
	public UserContext getUserContext() {
		return userContext;
	}
}
