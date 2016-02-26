package com.econtact.dataModel.data.filter;

import java.util.Collection;

public class FilterDefInList extends AbstractFieldFilterDef {
	private static final long serialVersionUID = -6870627412758111296L;
	private final Collection<?> value;
	
	public FilterDefInList(String fieldName, Collection<?> value) {
		super(FilterDefEnum.IN_LIST, fieldName);
		this.value = value;
	}

	public Collection<?> getValue() {
		return value;
	}
}
