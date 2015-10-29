package com.econtact.dataModel.data.util;

import java.sql.SQLException;

public class UniqueConstraintException extends Exception {
	private static final long serialVersionUID = -4782182423251259877L;
	public static final String POSTGRESQL_UNIQUE_CONSTRAINT_EXCEPTION_CODE = "23505";
	
	private Class entityClass;

	public UniqueConstraintException(Class entityClass, SQLException exception) {
		super(exception);
		this.entityClass = entityClass;
	}

	public Class getEntityClass() {
		return entityClass;
	}
}
