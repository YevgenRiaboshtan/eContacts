package com.econtact.authWeb.app.helpers;

import java.io.Serializable;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserEntity;

@ManagedBean
public class WebHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String DEF_ADMIN_PAGE = "quick_start/list.jsf";
	public static final String DEFAULT_PAGE = "index.jsf";

	public boolean isAuth() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}

	public String getUserName() {
		String userName = "";
		if (isAuth()) {
			userName = ((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();
		}
		return userName;
	}

	public boolean isAccessForUser() {
		return isAccessForRole(RoleType.ROLE_REGISTER);
	}

	public boolean isAccessForAdmin() {
		return isAccessForRole(RoleType.ROLE_ADMIN);
	}

	private boolean isAccessForRole(RoleType role) {
		if (!isAuth() || SecurityContextHolder.getContext().getAuthentication().getAuthorities().isEmpty()) {
			return false;
		}
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority(role.getName()));
	}
	
	public static <T> T getBean(final Class<T> beanClass) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean(beanClass);
	}
	
	public static UserEntity getPrincipal() {
		return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static UserContext getUserContext() {
		final TimeZone userTimeZone = TimeZone.getTimeZone("GMT+2");
		final UserContext result = UserContext.create(getPrincipal(), userTimeZone);
		return result;
	}
	
}
