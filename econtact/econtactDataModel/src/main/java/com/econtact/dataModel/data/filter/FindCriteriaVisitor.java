package com.econtact.dataModel.data.filter;

public interface FindCriteriaVisitor<T extends Filter> {
	
	void processFilter(T filter);
}
