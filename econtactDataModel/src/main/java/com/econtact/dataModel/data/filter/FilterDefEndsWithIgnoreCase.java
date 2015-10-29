package com.econtact.dataModel.data.filter;

public class FilterDefEndsWithIgnoreCase extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 1681241549471210769L;
	
	private final String value;
	
	public FilterDefEndsWithIgnoreCase(String fieldName, String value) {
		super(FilterDefEnum.ENDS_WITH_IGNORE_CASE, fieldName);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
