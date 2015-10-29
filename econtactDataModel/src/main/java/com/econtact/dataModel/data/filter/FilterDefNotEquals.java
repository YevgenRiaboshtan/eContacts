package com.econtact.dataModel.data.filter;

public class FilterDefNotEquals extends AbstractFieldFilterDef {
	private static final long serialVersionUID = 4823223288109523562L;

	private final Object value;

	public FilterDefNotEquals(String fieldName, Object value) {
		super(FilterDefEnum.NOT_EQUALS, fieldName);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
