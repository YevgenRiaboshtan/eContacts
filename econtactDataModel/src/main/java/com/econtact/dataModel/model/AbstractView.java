package com.econtact.dataModel.model;

import java.io.Serializable;

/**
 * Интерфейс для получения идентификатора представления
 * Interface for getting of the view.
 * @author evgeniy
 *
 * @param <PK> - тип идентификтаора записи. Id`s type.
 */
public interface AbstractView<PK extends Serializable> extends Serializable {
	
	/**
	 * Возращает идентификатор объекта представления.
	 * Return id of the view.
	 * @return - идентификатор. Id.
	 */
	PK getId();
}
