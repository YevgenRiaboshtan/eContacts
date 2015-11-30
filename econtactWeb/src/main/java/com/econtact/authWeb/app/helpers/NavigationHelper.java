package com.econtact.authWeb.app.helpers;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "navigationHelper")
@RequestScoped
public class NavigationHelper {

	public void navigate(String page) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(page);
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
