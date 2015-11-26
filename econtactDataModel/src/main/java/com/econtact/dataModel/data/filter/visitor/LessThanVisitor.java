package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefLess;

public class LessThanVisitor extends AbstractVisitor<FilterDefLess>{

	LessThanVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefLess filter) {
		predicate = cbCtx.getCriteriaBuilder().lt(
					getPath(filter.getFieldName()), 
					cbCtx.createFindParam(filter.getValue()));
	}
}
