package com.econtact.dataModel.data.filter;

public class AbstractFieldFilterDef extends AbstractFilterDef {
	private static final long serialVersionUID = 6676361803041284994L;

	private final String fieldName;

	public AbstractFieldFilterDef(FilterDefEnum type, String fieldName) {
		super(type);
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
