package com.econtact.authWeb.app.helpers;

import java.io.Serializable;
import java.util.List;
import java.util.TimeZone;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.MenuModel;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.authWeb.app.beans.view.UserSessionBean;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Named(value = "webHelper")
@SessionScoped
public class WebHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
	
	public static final String SUPER_ADMIN_PAGE = "superAdmin/adminsList.jsf";
	public static final String INDEX_PAGE = "index.jsf";
	public static final String LOGIN_PAGE = "loginPage.jsf";

	@Inject
	UserSessionBean userSession;
	
	@Inject
	FilterHelper filterHelper;

	public boolean isAuth() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
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

	public List<SelectItem> getSelectOneItemForEnum(String enumClassName, boolean optional) throws ClassNotFoundException {
		return filterHelper.getEnumFilter(enumClassName, optional);
	}
	
	public MenuModel buildModel() {
		return userSession.getTopMenuModel();
	}
}
