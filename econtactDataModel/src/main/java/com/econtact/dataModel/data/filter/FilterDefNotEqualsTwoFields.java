package com.econtact.dataModel.data.filter;

public class FilterDefNotEqualsTwoFields extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 7563399045153997286L;

	private final String anotherField;

	public FilterDefNotEqualsTwoFields(String fieldName, String anotherField) {
		super(FilterDefEnum.NOT_EQUALS_TWO_FIELDS, fieldName);
		this.anotherField = anotherField;
	}

	public String getAnotherField() {
		return anotherField;
	}

}
