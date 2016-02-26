package com.econtact.dataModel.data.filter;

import java.util.Collection;

public class FilterDefNotInList extends AbstractFieldFilterDef {
	private static final long serialVersionUID = -5021325296185981353L;
	private final Collection<?> value;

	public FilterDefNotInList(String fieldName, Collection<?> value) {
		super(FilterDefEnum.NOT_IN_LIST, fieldName);
		this.value = value;
	}

	public Collection<?> getValue() {
		return value;
	}
}
