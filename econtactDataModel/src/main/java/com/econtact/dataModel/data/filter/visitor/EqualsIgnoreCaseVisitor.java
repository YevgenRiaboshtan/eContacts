package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefEqualsIgnoreCase;

public class EqualsIgnoreCaseVisitor extends AbstractVisitor<FilterDefEqualsIgnoreCase> {

	EqualsIgnoreCaseVisitor(VisitorContext ctx) {
		super(ctx);
	}

	@Override
	public void processFilter(FilterDefEqualsIgnoreCase filter) {
		predicate = cbCtx.getCriteriaBuilder().like(cbCtx.getCriteriaBuilder().upper(getPath(filter.getFieldName())),
				cbCtx.createFindParam(filter.getValue().toUpperCase()));
	}
}
