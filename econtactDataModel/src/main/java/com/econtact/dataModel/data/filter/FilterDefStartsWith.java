package com.econtact.dataModel.data.filter;

public class FilterDefStartsWith extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 5960771353779065030L;

	private final String value;

	public FilterDefStartsWith(String fieldName, String value) {
		super(FilterDefEnum.STARTS_WITH, fieldName);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
