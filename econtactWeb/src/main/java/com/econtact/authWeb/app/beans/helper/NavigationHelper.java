package com.econtact.authWeb.app.beans.helper;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import com.econtact.dataModel.model.entity.accout.RoleType;

@Named(value = "navigationHelper")
@ApplicationScoped
public class NavigationHelper {
	public static final String MODIFY_NOT_ALLOWED_PAGE = "/modifyNotAllowed.jsf";
	public static final String INDEX_PAGE = "index.jsf";
	public static final String LOGIN_PAGE = "/loginPage.jsf";
	public static final String ID_PARAM = "id";

	public void navigate(String page) throws IOException {
		navigate(page, null);
	}
	
	public void navigate(String page, String id) throws IOException {
		if (StringUtils.isNotBlank(id)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getRootPath() + page + "?" + ID_PARAM + "=" + id);
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getRootPath() + page);
		}
	}
	
	public void navigateToProfile(RoleType role) throws IOException {
		StringBuffer page = new StringBuffer();
		switch (role) {
		case ROLE_SUPER_ADMIN:
			page.append("/superAdmin/showProfile.jsf");
			break;
		case ROLE_ADMIN:
			page.append("/admin/showProfile.jsf");
			break;
		default:
			page.append(getIndexPage());
			break;
		}
		navigate(page.toString());
	}
	
	public String getIndexPage() {
		return new StringBuffer(getRootPath()).append("/").append(INDEX_PAGE).toString();
	}
	
	private String getRootPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
}
