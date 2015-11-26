package com.econtact.dataModel.data.filter;

public class FilterDefLessEq extends AbstractFieldFilterDef{
	private static final long serialVersionUID = 7828699684449614023L;

	private final Object value;
	
	public FilterDefLessEq(String fieldName, Object value) {
		super(FilterDefEnum.LESS_THAN_OR_EQUALS, fieldName);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
