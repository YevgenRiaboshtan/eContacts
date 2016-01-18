package com.econtact.authWeb.app.beans.view;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import com.econtact.authWeb.app.beans.helper.LabelsHelper;
import com.econtact.authWeb.app.beans.helper.NavigationHelper;
import com.econtact.authWeb.app.constraint.ContraintViewRelation;
import com.econtact.authWeb.app.utils.UniqueConstraintHandleUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.AbstractEntity;

public abstract class GeneralCRUDBean<T extends AbstractEntity> implements Serializable {
	private static final long serialVersionUID = 1839876621381844278L;

	@Inject
	protected NavigationHelper navigationHelper;
	
	@Inject
	LabelsHelper labelsHelper;
	
	@Inject
	protected UserSessionBean userSession;
	
	@EJB
	GenericService genericService;
	
	protected T entity;
	private Class<T> entityClass;
	
	@PostConstruct
	public void init() {
		entityClass = (Class<T>) getParameterClass( 0, getClass());
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	
	public void save() throws IOException {
		preSave();
		try {
			genericService.saveOrUpdate(entity, userSession.getUserContext());
			afterSaveNavigate();
		} catch (EJBException e) {
			if (e.getCause() instanceof OptimisticLockException) {
				//TODO handle optimistick lock exception
				
			} else {
				throw e;
			}
		} catch (UniqueConstraintException e) {
			ContraintViewRelation relation = UniqueConstraintHandleUtils.getInstance().handleException(e);
			FacesContext.getCurrentInstance().addMessage(relation.getIdField(),
					new FacesMessage(labelsHelper.getLocalizedMessage(relation.getErrorMessageKey())));
		}
	}
	
	public void cancel() throws IOException {
		cancelNavigate();
	}
	
	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	protected void cancelNavigate() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	protected void preSave() {
	}
	
	private Class<?> getParameterClass(int pos, Class<?> target) {
		return (Class<?>) ((ParameterizedType) target.getGenericSuperclass())
				.getActualTypeArguments()[pos];
	}
}