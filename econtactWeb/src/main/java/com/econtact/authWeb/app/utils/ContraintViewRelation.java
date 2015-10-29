package com.econtact.authWeb.app.utils;

import java.io.Serializable;

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
