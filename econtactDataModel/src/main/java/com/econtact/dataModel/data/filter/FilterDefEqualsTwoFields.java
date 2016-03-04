package com.econtact.dataModel.data.filter;

public class FilterDefEqualsTwoFields extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 3443562949969414228L;
	private final String anotherField;

	public FilterDefEqualsTwoFields(String fieldName, String anotherField) {
		super(FilterDefEnum.EQUALS_TWO_FIELDS, fieldName);
		this.anotherField = anotherField;
	}

	public String getAnotherField() {
		return anotherField;
	}
}
