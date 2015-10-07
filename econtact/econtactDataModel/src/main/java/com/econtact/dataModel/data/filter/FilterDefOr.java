package com.econtact.dataModel.data.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterDefOr extends AbstractFilterDef {
	private static final long serialVersionUID = 4370976057034373941L;
	
	private final List<AbstractFilterDef> filterDefs;
	
	public FilterDefOr(AbstractFilterDef... filterDefs) {
		super(FilterDefEnum.OR);
		this.filterDefs = new ArrayList<AbstractFilterDef>(Arrays.asList(filterDefs));
	}
	
	public FilterDefOr() {
		this(new AbstractFilterDef[0]);
	}
	
	public List<AbstractFilterDef> getFilterDefs() {
		return filterDefs;
	}
	
	public void addDefinition(AbstractFilterDef filterDef) {
		filterDefs.add(filterDef);
	}
	
	@Override
	public boolean isEmpty() {
		return filterDefs.isEmpty();
	}
}
