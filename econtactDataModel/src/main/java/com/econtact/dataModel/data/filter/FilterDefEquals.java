package com.econtact.dataModel.data.filter;

public class FilterDefEquals extends AbstractFieldFilterDef {
	private static final long serialVersionUID = -6445470813046310868L;
	private final Object value;

	public FilterDefEquals(String fieldName, Object value) {
		super(FilterDefEnum.EQUALS, fieldName);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
