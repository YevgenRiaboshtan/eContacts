package com.econtact.dataModel.data.filter;

public class FilterDefLess extends AbstractFieldFilterDef{
	private static final long serialVersionUID = -527451162440164619L;
	
	private final Object value;
	
	public FilterDefLess(String fieldName, Object value) {
		super(FilterDefEnum.LESS_THAN, fieldName);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
