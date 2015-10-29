package com.econtact.dataModel.data.filter;

public class FilterDefNotNull extends AbstractFieldFilterDef {
	private static final long serialVersionUID = -1910682917828488836L;

	public FilterDefNotNull(String fieldName) {
		super(FilterDefEnum.NOT_NULL, fieldName);
	}
}
