package com.econtact.authWeb.app.helpers;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "navigationHelper")
@ApplicationScoped
public class NavigationHelper {

	public void navigate(String page) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(getRootPath() + page);
	}

	public void navigateToProfile(boolean edit) throws IOException {
		StringBuffer page = new StringBuffer();
		switch (WebHelper.getPrincipal().getRole()) {
		case ROLE_SUPER_ADMIN:
			page.append(getRootPath()).append(edit ? "/superAdmin/profile/editProfile.jsf" : "/superAdmin/profile/showProfile.jsf");	
			break;
		default:
			page.append(getIndexPage());
			break;
		}
		navigate(page.toString());
	}
	
	private String getRootPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
	
	public String getIndexPage() {
		return new StringBuffer(getRootPath()).append("/").append(WebHelper.INDEX_PAGE).toString();
	}
}
