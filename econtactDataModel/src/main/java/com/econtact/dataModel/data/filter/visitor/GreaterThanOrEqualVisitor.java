package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefGreaterEq;

public class GreaterThanOrEqualVisitor extends AbstractVisitor<FilterDefGreaterEq>{

	protected GreaterThanOrEqualVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefGreaterEq filter) {
		predicate = cbCtx.getCriteriaBuilder().ge(
				getPath(filter.getFieldName()),
				cbCtx.createFindParam(filter.getValue()));
	}

}
