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
 * 
 * Абстрактный бин для CRUD бинов, расширяет {@link AbstractViewBean}
 * Имеет базовыые методы сохранения сущности
 * 
 * @author Yevgen Riaboshtan
 *
 * @param <T> - system entity extends {@link AbstractEntity}
 * 				- Сущность системы, расширяющая {@link AbstractEntity}
 */
public abstract class AbstractCRUDBean<T extends AbstractEntity> extends AbstractViewBean implements Serializable {
	private static final long serialVersionUID = 1839876621381844278L;

	/**
	 * Navigation bean instance. {@link NavigationHelper}
	 * 
	 * Экземпляр навигационного бина. {@link NavigationHelper}
	 */
	@Inject
	protected NavigationHelper navigationHelper;
	
	/**
	 * Label helper bean instance. {@link LabelsHelper}
	 * 
	 * Экземпляр бина получения локализированных сообщений. {@link LabelsHelper}
	 */
	@Inject
	protected LabelsHelper labelsHelper;
	
	/**
	 * Generic service bean instance. {@link GenericService}
	 * 
	 * Экземпляр базового бина операций с сущностями {@link GenericService}
	 */
	@EJB
	protected GenericService genericService;
	
	/**
	 * Entity instance.
	 * 
	 * Сущность.
	 */
	protected T entity;
	/**
	 * Entities class name.
	 * 
	 * Класс сущности.
	 */
	private Class<T> entityClass;
	/**
	 * If occur {@link OptimisticLockException} exception at save.
	 * 
	 * поле указывающее возникло ли исключение {@link OptimisticLockException} при сохранении.
	 */
	private boolean optimistickLockException = false;
	
	/**
	 * Initial bean method
	 * Check if request have id param then find entity with this id and set into entity field.
	 * if id parameter is absent create default entity and set it into entity field.
	 * 
	 * Метод инициализации бина.
	 * Если GET запрос имеет параметр id, то загружается сущность с указанным id,
	 * иначе создается новый екземпляр сущности.
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
				cancelNavigate();
			}
		} else {
			setEntity(createDefaultEntity());
		}
	}
	
	/**
	 * Return entity
	 * 
	 * Возвращает сущность
	 * 
	 * @return - entity
	 * 			- сущность
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * Set entity
	 * 
	 * Устанавливает сущность 
	 * 
	 * @param entity - entity
	 * 				- сущность
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	/**
	 * Return optimistickLocException field value.
	 * 
	 * Возвращает поле указывающее возникало ли исключение типа {@link OptimisticLockException}.
	 * 
	 * @return - optimistickLocException field value
	 * 			- поле optimistickLockException.
	 */
	public boolean isOptimistickLockException() {
		return optimistickLockException;
	}

	/**
	 * General save method.
	 * Save process consists of several steps.
	 * 1. Validate - custom validate by {@link AbstractCRUDBean#validate()} method.
	 * 2. Presave operation - {@link AbstractCRUDBean#preSave()} method.
	 * 3. Save entity through  {@link AbstractCRUDBean#saveOrUpdate(AbstractEntity, UserContext)} method
	 * 4. Navigate after saving to another page. {@link AbstractCRUDBean#afterSaveNavigate()}
	 * If validation fail next steps are not executed.
	 * 
	 * азовый метод созранения сущности.
	 * Процесс созранения состоит из нескольких этапов.
	 * 1. Валидация - дополнительная валидация на сервере, метод - {@link AbstractCRUDBean#validate()}.
	 * 2. Обработка перед сохранением - дополнительные действия выполняемые перед сохранением сущности, метод  - {@link AbstractCRUDBean#preSave()}.
	 * 3. Сохранение с помощбю метода {@link AbstractCRUDBean#saveOrUpdate(AbstractEntity, UserContext)}.
	 * 4. Переход на другую страницу после сохранения, метод - {@link AbstractCRUDBean#afterSaveNavigate()}.
	 * Если этап валидации не пройден, остальные этамы не выполняются.
	 * 
	 * @throws IOException - from after navigation method. @see {@link AbstractCRUDBean#afterSaveNavigate()}
	 * 						- Может возникнуть в методе навигации. @see {@link AbstractCRUDBean#afterSaveNavigate()}
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
	 * 
	 * Метод навигации на другую страницу без сохранения сущности.
	 * 
	 * @throws IOException - @see {@link AbstractCRUDBean#cancelNavigate()}
	 * 						- @see {@link AbstractCRUDBean#cancelNavigate()}
	 */
	public void cancel() throws IOException {
		cancelNavigate();
	}
	
	/**
	 * Method reload entity from database after {@link OptimisticLockException} occur.
	 * 
	 * Метод перезагружающий сущность с базы данных после возникновения {@link OptimisticLockException}. 
	 */
	public void refresh() {
		this.entity = genericService.findById(entityClass, entity.getId(), getDefaultEntityGraph());
		optimistickLockException = false;
	}
	
	/**
	 * Method to save entity, default save through {@link GenericService#saveOrUpdate(AbstractEntity, UserContext)}.
	 * 
	 * Метод сохранения сущности, по умолчанию выполняется с помощью {@link GenericService#saveOrUpdate(AbstractEntity, UserContext)} метода.
	 * 
	 * @param entity - entity to save.
	 * 				- сущность для сохранения.
	 * @param userContext - user context that execute save.
	 * 						- контекст пользователя роизводящего изменения.
	 * @return - new instance ot the entity.
	 * 			- новое состояние сущности после сохранения.
	 * 
	 * @throws UniqueConstraintException - occurs if violation of the unique fields need be handle.
	 * 									 - возникает при сохранении, если было нарушено условие уникальности полей.  
	 */
	protected T saveOrUpdate(T entity, UserContext userContext) throws UniqueConstraintException {
		return genericService.saveOrUpdate(entity, userContext);
	}
	
	/**
	 * Navigate to custom page after saving.
	 * 
	 * Навигация после сохранения.
	 */
	protected void afterSaveNavigate() {
	}
	
	/**
	 * Navigate to custom page on cancel without saving entity.
	 * 
	 * Навигация на страницу при отмене действий.
	 */
	protected void cancelNavigate() {
	}
	
	/**
	 * Method to provide custom preparation after validation and before save entity.
	 * 
	 * Метод для выполнения дополнительных дествий перед созранением.
	 */
	protected void preSave() {
	}
	
	/**
	 * Validation method to execute custom server side validation.
	 * 
	 * Метод дополнительной валидации на сервере.
	 * 
	 * @return - true if validation complete, false if fail.
	 * 			- true - сли валидация пройдена успешно, иначе - false.
	 */
	protected boolean validate() {
		return true;
	}
	
	/**
	 * Method return default entity graph name to set it at load entity from database in init method.
	 * 
	 * Метод возвращает название графа сущности применяемый при загрузке сущности из базы данных.
	 * 
	 * @return - default entity graph name
	 * 			- название графа сущности.
	 */
	protected String getDefaultEntityGraph() {
		return null;
	}
	
	/**
	 * Method check if current user can modify entity.
	 * 
	 * Метод выполняющий проверку разрешена ли текущему пользователю редактировать указанную сущность.
	 * 
	 * @param entity - entity to check.
	 * 				- сущность для проверки.
	 * @return - true - if user can modify current entity, else - false.
	 * 			- true - если пользователь имеет право на редктирование, иначе - false.
	 */
	abstract protected boolean canModifyEntity(T entity);
	
	/**
	 * Method create default entity for creating new object.
	 * 
	 * Метод создающий новую сущность.
	 * 
	 * @return - new entity.
	 * 			- созданная сущность.
	 */
	abstract protected T createDefaultEntity();	
	
	/**
	 * Method find class name from Bean parameter.
	 * 
	 * Метод поиска названия класса с параметра бина.
	 * 
	 * @param target - Bean class name
	 * 				- название класса бина.
	 * @return - entity class name.
	 * 			- возвращаемое название искомого класса сущности.
	 * 
	 * @throws IllegalArgumentException if parameter not found
	 * 									если название параметризированного класса сущности не было найдено.
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
	 * 
	 * Метод выполняющий проверку доступновти редактирования текущим пользователем сущности прежде загрузки страницы.
	 * Если пользователь не имеет права редактирования сущности он будет перенаправлен на страницу {@link NavigationHelper#MODIFY_NOT_ALLOWED_PAGE}.
	 * 
	 * @param event - event to check.
	 * 				- событие для проверки.
	 * @throws IOException - @see {@link NavigationHelper#navigate(String)}.
	 */
	public void isAllowEdit(ComponentSystemEvent event) throws IOException{
		if (!canModifyEntity(getEntity())) {
			navigationHelper.navigate(NavigationHelper.MODIFY_NOT_ALLOWED_PAGE);
		}
	}
}
