package com.econtact.dataModel.data.filter;

public class FilterDefIsNull extends AbstractFieldFilterDef {
	private static final long serialVersionUID = -1910682917828488836L;

	public FilterDefIsNull(String fieldName) {
		super(FilterDefEnum.IS_NULL, fieldName);
	}
}
