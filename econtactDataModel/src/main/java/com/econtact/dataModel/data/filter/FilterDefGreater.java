package com.econtact.dataModel.data.filter;

public class FilterDefGreater extends AbstractFieldFilterDef{
	private static final long serialVersionUID = 804814117021456917L;

	private final Object value;
	
	public FilterDefGreater(String fieldName, Object value) {
		super(FilterDefEnum.GREATER_THAN, fieldName);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
