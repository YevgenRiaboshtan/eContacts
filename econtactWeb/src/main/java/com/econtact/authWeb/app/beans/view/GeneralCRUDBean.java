package com.econtact.authWeb.app.beans.view;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.apache.commons.lang.StringUtils;

import com.econtact.authWeb.app.beans.helper.LabelsHelper;
import com.econtact.authWeb.app.beans.helper.NavigationHelper;
import com.econtact.authWeb.app.constraint.ContraintViewRelation;
import com.econtact.authWeb.app.utils.UniqueConstraintHandleUtils;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.AbstractEntity;

public abstract class GeneralCRUDBean<T extends AbstractEntity> extends AbstractViewBean implements Serializable {
	private static final long serialVersionUID = 1839876621381844278L;

	@Inject
	protected NavigationHelper navigationHelper;
	
	@Inject
	LabelsHelper labelsHelper;
	
	@EJB
	protected GenericService genericService;
	
	protected T entity;
	private Class<T> entityClass;
	private boolean optimistickLockException = false;
	
	@PostConstruct
	public void init() throws IOException {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(NavigationHelper.ID_PARAM);
		if (StringUtils.isNotBlank(id)) {
			entityClass = (Class<T>) getParameterClass( 0, getClass());
			T entity = genericService.findById(entityClass, BigDecimal.valueOf(Long.parseLong(id)), getDefaultEntityGraph()); 
			if (entity != null) {
				setEntity(entity);
			} else {
				cancelNavigate();
			}
		} else {
			setEntity(createDefaultEntity());
		}
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public boolean isOptimistickLockException() {
		return optimistickLockException;
	}

	public void save() throws IOException {
		try {
			preSave();
			entity = saveorUpdate(entity, userSessionBean.getUserContext());
			afterSaveNavigate();
		} catch (EJBException e) {
			if (e.getCause() instanceof OptimisticLockException) {
				FacesMessage optimisticsMsg = new FacesMessage(labelsHelper.getLocalizedMessage(LocaleLabels.OPTIMISTIC_LOCK_EXCEPTION_MESSAGE));
				optimisticsMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, optimisticsMsg);
				optimistickLockException = true;
			} else {
				throw e;
			}
		} catch (UniqueConstraintException e) {
			ContraintViewRelation relation = UniqueConstraintHandleUtils.getInstance().handleException(e);
			FacesMessage errorMessage = new FacesMessage(labelsHelper.getLocalizedMessage(relation.getErrorMessageKey()));
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		} 
	}
	
	public void cancel() throws IOException {
		cancelNavigate();
	}
	
	public void refresh() throws IOException {
		this.entity = genericService.findById(entityClass, entity.getId(), getDefaultEntityGraph());
		optimistickLockException = false;
	}
	
	protected T saveorUpdate(T entity, UserContext userContext) throws UniqueConstraintException {
		return genericService.saveOrUpdate(entity, userContext);
	}
	
	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	protected void cancelNavigate() throws IOException {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	protected void preSave() {
	}
	
	protected String getDefaultEntityGraph() {
		return null;
	}
	
	abstract protected boolean canModifyEntity(T entity);
	
	abstract protected T createDefaultEntity();	
	
	private Class<?> getParameterClass(int pos, Class<?> target) {
		Class current = target;
		while (current.getSuperclass() != null) {
			if (current.getGenericSuperclass() instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) current.getGenericSuperclass())
						.getActualTypeArguments()[pos];
			}
			current = current.getSuperclass();
		}
		throw new IllegalArgumentException("Unknown parameter type");
	}
	
	public void isAllowEdit(ComponentSystemEvent event) throws IOException{
		if (!canModifyEntity(getEntity())) {
			navigationHelper.navigate(NavigationHelper.MODIFY_NOT_ALLOWED_PAGE);
		}
	}
}
