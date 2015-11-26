package com.econtact.dataModel.data.filter;

public class FilterDefGreaterEq extends  AbstractFieldFilterDef{
	private static final long serialVersionUID = -7325271836617763131L;
	
	private final Object value;
	
	public FilterDefGreaterEq(String fieldName, Object value) {
		super(FilterDefEnum.GREATER_THAN_OR_EQUALS, fieldName);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
