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

/**
 * Abstract bean class, extends {@link AbstractViewBean}.
 * @author Yevgen Riaboshtan
 *
 * @param <T> - system entity extends {@link AbstractEntity}
 */
public abstract class AbstractCRUDBean<T extends AbstractEntity> extends AbstractViewBean implements Serializable {
	private static final long serialVersionUID = 1839876621381844278L;

	/**
	 * Navigation bean instance. {@link NavigationHelper}
	 */
	@Inject
	protected NavigationHelper navigationHelper;
	
	/**
	 * Label helper bean instance.
	 */
	@Inject
	protected LabelsHelper labelsHelper;
	
	/**
	 * Generic service bean instance.
	 */
	@EJB
	protected GenericService genericService;
	
	/**
	 * Entity instance.
	 */
	protected T entity;
	/**
	 * Entities class name.
	 */
	private Class<T> entityClass;
	/**
	 * If occur {@link OptimisticLockException} exception at save
	 */
	private boolean optimistickLockException = false;
	
	/**
	 * Initial bean method
	 * Check if request have id param then find entity with this id and set into entity field.
	 * if id parameter is absent create default entity and set it into entity field.
	 * 
	 * @see PostConstruct
	 */
	@PostConstruct
	public void init() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(NavigationHelper.ID_PARAM);
		if (StringUtils.isNotBlank(id)) {
			entityClass = (Class<T>) getParameterClass(getClass());
			T entity = genericService.findById(entityClass, BigDecimal.valueOf(Long.parseLong(id)), getDefaultEntityGraph()); 
			if (entity != null) {
				setEntity(entity);
			} else {
				try {
					cancelNavigate();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			setEntity(createDefaultEntity());
		}
	}
	
	/**
	 * Return entity
	 * @return - entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * Set entity
	 * @param entity - entity
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	/**
	 * Return optimistickLocException field value
	 * @return - optimistickLocException field value
	 */
	public boolean isOptimistickLockException() {
		return optimistickLockException;
	}

	/**
	 * General save method.
	 * Save process consists of several steps.
	 * 1. Validate - custom validate by {@link AbstractCRUDBean#validate()} method.
	 * 2. presave operation - {@link AbstractCRUDBean#preSave()} method.
	 * 3. save entity through  {@link AbstractCRUDBean#saveOrUpdate(AbstractEntity, UserContext)} method
	 * 4. navigate after saving to another page. {@link AbstractCRUDBean#afterSaveNavigate()}
	 * If validation fail next steps are not executed.
	 * @throws IOException - from after navigation method. @see {@link AbstractCRUDBean#afterSaveNavigate()}
	 */
	public void save() throws IOException {
		try {
			if (!validate()) {
				return;
			}
			preSave();
			entity = saveOrUpdate(entity, userSessionBean.getUserContext());
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
	
	/**
	 * Method navigate to another page without saving.
	 * @throws IOException - @see {@link AbstractCRUDBean#cancelNavigate()}
	 */
	public void cancel() throws IOException {
		cancelNavigate();
	}
	
	/**
	 * Method reload entity from database after {@link OptimisticLockException} occur.
	 */
	public void refresh() {
		this.entity = genericService.findById(entityClass, entity.getId(), getDefaultEntityGraph());
		optimistickLockException = false;
	}
	
	/**
	 * 
	 * @param entity
	 * @param userContext
	 * @return
	 * @throws UniqueConstraintException
	 */
	protected T saveOrUpdate(T entity, UserContext userContext) throws UniqueConstraintException {
		return genericService.saveOrUpdate(entity, userContext);
	}
	
	/**
	 * Navigate to custom page after saving.
	 * @throws IOException - @see {@link NavigationHelper#navigate(String)}
	 */
	protected void afterSaveNavigate() throws IOException {
	}
	
	/**
	 * Navigate to custom page on cancel without saving entity.
	 * @throws IOException
	 */
	protected void cancelNavigate() throws IOException {
	}
	
	/**
	 * Method to provide custom preparation after validation and before save entity.
	 */
	protected void preSave() {
	}
	
	/**
	 * Validation method to execute custom server side validation.
	 * @return - true if validation complete, false if fail.
	 */
	protected boolean validate() {
		return true;
	}
	
	/**
	 * Method return default entity graph name to set it at load entity from database in init method.
	 * @return - default entoty graph name
	 */
	protected String getDefaultEntityGraph() {
		return null;
	}
	
	/**
	 * Method check if current user can modify entity.
	 * @param entity - entity to check.
	 * @return - true - if user can modify current entity, else - false.
	 */
	abstract protected boolean canModifyEntity(T entity);
	
	/**
	 * Method create default entity for creating new object.
	 * @return - new entity.
	 */
	abstract protected T createDefaultEntity();	
	
	/**
	 * Method find class name from Bean parameter.
	 * @param target - Bean class name
	 * @return - entity class name.
	 * 
	 * @throws IllegalArgumentException if parameter not found
	 */
	private Class<?> getParameterClass(Class<?> target) {
		Class<?> current = target;
		while (current.getSuperclass() != null) {
			if (current.getGenericSuperclass() instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) current.getGenericSuperclass())
						.getActualTypeArguments()[0];
			}
			current = current.getSuperclass();
		}
		throw new IllegalArgumentException("Unknown parameter type");
	}
	
	/**
	 * Method check if user can modify current entity before page was load.
	 * If don`t have user will be navigate to {@link NavigationHelper#MODIFY_NOT_ALLOWED_PAGE} page.
	 * @param event - event to check.
	 * @throws IOException - @see {@link NavigationHelper#navigate(String)}.
	 */
	public void isAllowEdit(ComponentSystemEvent event) throws IOException{
		if (!canModifyEntity(getEntity())) {
			navigationHelper.navigate(NavigationHelper.MODIFY_NOT_ALLOWED_PAGE);
		}
	}
}
