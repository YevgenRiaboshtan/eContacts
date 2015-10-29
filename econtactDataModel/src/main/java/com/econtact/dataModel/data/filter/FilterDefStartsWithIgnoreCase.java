package com.econtact.dataModel.data.filter;

public class FilterDefStartsWithIgnoreCase extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 1681241549471210769L;
	
	private final String value;
	
	public FilterDefStartsWithIgnoreCase(String fieldName, String value) {
		super(FilterDefEnum.STARTS_WITH_IGNORE_CASE, fieldName);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
