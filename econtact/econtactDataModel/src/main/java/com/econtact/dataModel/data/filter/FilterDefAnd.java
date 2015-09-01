package com.econtact.dataModel.data.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterDefAnd extends AbstractFilterDef {
	private static final long serialVersionUID = 1L;
	
	private final List<AbstractFilterDef> filterDefs;
	
	public FilterDefAnd(AbstractFilterDef... filterDefs) {
		super(FilterDefEnum.AND);
		this.filterDefs = new ArrayList<AbstractFilterDef>(Arrays.asList(filterDefs));
	}

	public FilterDefAnd() {
		this(new AbstractFilterDef[0]);
	}

	public List<AbstractFilterDef> getFilterDefs() {
		return filterDefs;
	}

	public void addDefinition(AbstractFilterDef filterDef) {
		if (filterDef != null && !filterDef.isEmpty()) {
			filterDefs.add(filterDef);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return filterDefs.isEmpty();
	}
}
