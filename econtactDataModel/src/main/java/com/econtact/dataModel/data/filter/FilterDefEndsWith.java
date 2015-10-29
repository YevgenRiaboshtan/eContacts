package com.econtact.dataModel.data.filter;

public class FilterDefEndsWith extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 5960771353779065030L;

	private final String value;

	public FilterDefEndsWith(String fieldName, String value) {
		super(FilterDefEnum.ENDS_WITH, fieldName);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
