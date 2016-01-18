package com.econtact.authWeb.app.beans.view;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.primefaces.context.RequestContext;

import com.econtact.authWeb.app.helpers.FilterHelper;
import com.econtact.authWeb.app.helpers.LabelsHelper;
import com.econtact.authWeb.app.helpers.NavigationHelper;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.utils.ContraintViewRelation;
import com.econtact.authWeb.app.utils.UniqueConstraintHandleUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

public abstract class AbstractViewBean<T extends AbstractEntity> implements Serializable {
	private static final long serialVersionUID = 972909936401945467L;

	@Inject
	protected UserSessionBean userSessionBean;
	
	@Inject
	protected NavigationHelper navigationHelper;

	@Inject
	private FilterHelper filterHelper;
	
	@Inject
	private LabelsHelper labelsHelper;
	
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
	
	@PreDestroy
	public void destroy() {
		System.out.println("destroy");
	}
	
	public void saveEntity() throws IOException {
		preSave();
		try {
			entity = genericService.saveOrUpdate(entity, WebHelper.getUserContext());
			navigateAfterSave();
		} catch (EJBException e) {
			if (e.getCause() instanceof OptimisticLockException) {
				RequestContext.getCurrentInstance()
					.showMessageInDialog(
						new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							labelsHelper.getLocalizedMessage(LocaleLabels.COMMON_ERROR_MESSAGE),
							labelsHelper.getLocalizedMessage(LocaleLabels.OPTIMISTIC_LOCK_EXCEPTION_MESSAGE)));
				
			} else {
				throw e;
			}
		} catch (UniqueConstraintException e) {
			ContraintViewRelation relation = UniqueConstraintHandleUtils.getInstance().handleException(e);
			FacesContext.getCurrentInstance().addMessage(relation.getIdField(),
					new FacesMessage(labelsHelper.getLocalizedMessage(relation.getErrorMessageKey())));
		}
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		System.out.println("abstract view" + this);
		System.out.println("abstract view" + this.entity);
		System.out.println("abstract view" + entity + (entity == null ? "" : " " + entity.getId()));
		this.entity = entity;
	}
	
	protected void preSave() {
	}
	
	protected void navigateAfterSave() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	protected abstract T createDefaultEntity();
	
	protected abstract String getEditObjectPage();
	
	public void cancel() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	private Class<?> getParameterClass(int pos, Class<?> target) {
		return (Class<?>) ((ParameterizedType) target.getGenericSuperclass())
				.getActualTypeArguments()[pos];
	}

	public void editSelectedUser(AccountUserEntity user) throws IOException{
		userSessionBean.setEditedObject(user);
		navigationHelper.navigate(getEditObjectPage());
	}
	
	public FilterHelper getFilterHelper() {
		return filterHelper;
	}
}
