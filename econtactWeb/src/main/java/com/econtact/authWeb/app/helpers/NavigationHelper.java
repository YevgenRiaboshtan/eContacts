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

	public String getIndexPage() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/"
				+ WebHelper.INDEX_PAGE;
	}
}
