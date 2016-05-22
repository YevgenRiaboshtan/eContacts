package com.econtact.dataModel.model;

import java.io.Serializable;

/**
 * Interface represent system view.
 * Интерфейс определя.щий отображение системы.
 * @author evgeniy
 *
 * @param <PK> - Id`s type.
 * 				- Тип идентификатора
 */
@FunctionalInterface
public interface AbstractView<PK extends Serializable> extends Serializable {
	
	/**
	 * Return id of the view.
	 * Возвращает идентификатор представления.
	 * @return - Id.
	 * 			- Идентификатор
	 */
	PK getId();
}
