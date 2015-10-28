package com.econtact.authWeb.app.helpers;

import java.io.Serializable;
import java.util.TimeZone;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.faces.bean.ManagedBean;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

@ManagedBean
public class WebHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String DEF_ADMIN_PAGE = "quick_start/list.jsf";
	public static final String INDEX_PAGE = "index.jsf";
	
	
	public boolean isAuth() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}

	public String getUserName() {
		String userName = "";
		if (isAuth()) {
			userName = getPrincipal().getLogin();
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
		final BeanManager beanManager = CDI.current().getBeanManager();
		final Bean bean = beanManager.resolve(beanManager.getBeans(beanClass));
		CreationalContext cCtx = beanManager.createCreationalContext(bean);
		T result = (T) beanManager.getReference(bean, beanClass, cCtx);
		return result;
	}
	
	public static SessionUserEntity getPrincipal() {
		return (SessionUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static UserContext getUserContext() {
		final TimeZone userTimeZone = TimeZone.getTimeZone("GMT+2");
		final UserContext result = UserContext.create(getPrincipal(), userTimeZone);
		return result;
	}
	
	public ViewModeEnum getViewMode() {
		return ViewModeEnum.VIEW;
	}
	
	public ViewModeEnum getEditMode() {
		return ViewModeEnum.EDIT;
	}
	
	public ViewModeEnum getCreateMode() {
		return ViewModeEnum.CREATE;
	}
	
	public UserStatusEnum getActiveStatusEnum() {
		return UserStatusEnum.ENABLE;
	}
	
	public UserStatusEnum getDisableStatusEnum() {
		return UserStatusEnum.DISABLE;
	}
}
