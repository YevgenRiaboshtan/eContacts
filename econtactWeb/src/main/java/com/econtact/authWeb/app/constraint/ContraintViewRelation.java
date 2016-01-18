package com.econtact.authWeb.app.constraint;

import java.io.Serializable;

/**
 * POJO for relation unique constraint of the entity with key of the error message and input field name on page.
 * @author evgeniy
 *
 */
public class ContraintViewRelation implements Serializable {
	private static final long serialVersionUID = -2160068012106575745L;

	private final String constraintName;
	private final String idField;
	private final String errorMessageKey;

	public ContraintViewRelation(String constraintName, String idField, String errorMessageKey) {
		super();
		this.constraintName = constraintName;
		this.idField = idField;
		this.errorMessageKey = errorMessageKey;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public String getIdField() {
		return idField;
	}

	public String getErrorMessageKey() {
		return errorMessageKey;
	}
}
