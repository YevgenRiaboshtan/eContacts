package com.econtact.dataModel.data.filter;

public class FilterDefLike extends AbstractFieldFilterDef {
	private static final long serialVersionUID = -1874267991571887325L;
	private final String value;
	
	public FilterDefLike(String fieldName, String value) {
		super(FilterDefEnum.LIKE, fieldName);
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
