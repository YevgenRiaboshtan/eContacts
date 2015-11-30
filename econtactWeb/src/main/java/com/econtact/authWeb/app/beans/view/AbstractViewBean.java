package com.econtact.authWeb.app.beans.view;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.econtact.authWeb.app.helpers.FilterHelper;
import com.econtact.authWeb.app.helpers.LabelsHelper;
import com.econtact.authWeb.app.helpers.NavigationHelper;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.utils.ContraintViewRelation;
import com.econtact.authWeb.app.utils.UniqueConstraintHandleUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.AbstractEntity;

public abstract class AbstractViewBean<T extends AbstractEntity> implements Serializable {
	private static final long serialVersionUID = 972909936401945467L;

	@Inject
	protected UserSessionBean userSessionBean;
	
	@Inject
	protected NavigationHelper navigationHelper;

	@Inject
	private FilterHelper filterHelper;
	
	@EJB
	GenericService genericService;

	protected T entity;
	private Class<T> entityClass;

	@PostConstruct
	public void init() {
		entityClass = (Class<T>) getParameterClass( 0, getClass());
		if (userSessionBean.getEditedObject() != null) {
			setEntity(genericService.findById(entityClass, userSessionBean.getEditedObject().getId()));
			userSessionBean.setEditedObject(null);
		} else {
			setEntity(createDefaultEntity());
		}

	}
	
	public void saveEntity() throws IOException {
		preSave();
		try {
			entity = genericService.saveOrUpdate(entity, WebHelper.getUserContext());
		} catch (UniqueConstraintException e) {
			ContraintViewRelation relation = UniqueConstraintHandleUtils.getInstance().handleException(e);
			FacesContext.getCurrentInstance().addMessage(relation.getIdField(),
					new FacesMessage(LabelsHelper.getLocalizedMessage(relation.getErrorMessageKey())));
		}
		navigateAfterSave();
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	protected void preSave() {
	}
	
	protected void navigateAfterSave() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	protected abstract T createDefaultEntity();
	
	public void cancel() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	private Class<?> getParameterClass(int pos, Class<?> target) {
		return (Class<?>) ((ParameterizedType) target.getGenericSuperclass())
				.getActualTypeArguments()[pos];
	}

	public FilterHelper getFilterHelper() {
		return filterHelper;
	}
}
