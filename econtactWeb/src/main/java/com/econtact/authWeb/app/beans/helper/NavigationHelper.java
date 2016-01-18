package com.econtact.authWeb.app.beans.helper;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "navigationHelper")
@ApplicationScoped
public class NavigationHelper {
	public static final String SUPER_ADMIN_PAGE = "/superAdmin/adminsList.jsf";
	public static final String INDEX_PAGE = "index.jsf";
	public static final String LOGIN_PAGE = "loginPage.jsf";

	public void navigate(String page) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(getRootPath() + page);
	}

	public void navigateToProfile(boolean edit) throws IOException {
		StringBuffer page = new StringBuffer().append(getIndexPage());
		navigate(page.toString());
	}
	
	private String getRootPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
	
	public String getIndexPage() {
		return new StringBuffer(getRootPath()).append("/").append(INDEX_PAGE).toString();
	}
}
