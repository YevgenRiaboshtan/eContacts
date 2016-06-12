package com.econtact.authWeb.app.beans.helper;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import com.econtact.dataModel.model.entity.accout.RoleType;

/**
 * Navigation bean.
 * Has methods for navigate to another page. Use in JSF pages and other beans.
 * @author Yevgen Riaboshtan
 *
 */
@Named(value = "navigationHelper")
@ApplicationScoped
public class NavigationHelper {
	/**
	 * Target page for redirection user that does not has permission for edition some entities.
	 * \/modifyNotAllowed.xhtml page.
	 */
	public static final String MODIFY_NOT_ALLOWED_PAGE = "/modifyNotAllowed.jsf";
	/**
	 * System index page - index.xhtml
	 */
	public static final String INDEX_PAGE = "index.jsf";
	/**
	 * System login page - /loginPage.xhtml
	 */
	public static final String LOGIN_PAGE = "/loginPage.jsf";
	/**
	 * Parameter`s name, describe id of the editing entities.
	 */
	public static final String ID_PARAM = "id";

	/**
	 * Navigate to the page.
	 * @param page - target page.
	 */
	public void navigate(String page) {
		navigate(page, null);
	}
	
	/**
	 * Navigate to the edit page and add id parameter with value to GET request.
	 * @param page - target page.
	 * @param id - entity id
	 */
	public void navigate(String page, String id) {
		try {
			if (StringUtils.isNotBlank(id)) {	
				FacesContext.getCurrentInstance().getExternalContext().redirect(
						new StringBuilder(getRootPath()).append(page).append("?").append(ID_PARAM).append("=".intern()).append(id).toString());
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
						new StringBuilder(getRootPath()).append(page).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//FIXME add navigation to the xhtml menu configuration
	/**
	 * Redirect to the user edit profile page.
	 * @param role - curent users Role {@link RoleType}
	 * @throws IOException - for details @see {@link ExternalContext#redirect(String)}
	 */
	@Deprecated
	public void navigateToProfile(RoleType role) throws IOException {
		StringBuilder page = new StringBuilder(50);
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
	
	/**
	 * Method return index page of the system.
	 * {@link NavigationHelper#INDEX_PAGE}
	 * @return index page of the system
	 */
	public String getIndexPage() {
		return new String("/".intern() + INDEX_PAGE.intern()).intern();
	}
	
	/**
	 * Method return root path of the system. 
	 * @return - root path
	 */
	private String getRootPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	} 
}
