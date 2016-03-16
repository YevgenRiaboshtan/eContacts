package com.econtact.dataModel.model;

/**
 * Базовый инерфейс для перечислений.
 * Interface for the Enums.
 * @author evgeniy
 *
 */
public interface AbstractEnum {

	/**
	 * Возвращает ключ по которому будет отображаться локализированное название.
	 * Get locale label key.
	 * @return локализированный ключ. 
	 * @return locale label key.
	 */
	String getLabelKey();
	
	/**
	 * Возвращает значение
	 * Return value.
	 * @return - значение. 
	 * 			- value.
	 */
	Object getValue();
}
