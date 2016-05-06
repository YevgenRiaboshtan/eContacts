package com.econtact.dataModel.model;

import java.io.Serializable;

/**
 * Interface represent system view.
 * @author evgeniy
 *
 * @param <PK> - Id`s type.
 */
@FunctionalInterface
public interface AbstractView<PK extends Serializable> extends Serializable {
	
	/**
	 * Return id of the view.
	 * @return - Id.
	 */
	PK getId();
}
