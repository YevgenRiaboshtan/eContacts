package com.econtact.dataModel.data.filter;

public class FilterDefEqualsIgnoreCase extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 6518128780834519559L;
	private final String value;
	
	public FilterDefEqualsIgnoreCase(String fieldName, String value) {
		super(FilterDefEnum.EQUALS_IGNORE_CASE, fieldName);
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
