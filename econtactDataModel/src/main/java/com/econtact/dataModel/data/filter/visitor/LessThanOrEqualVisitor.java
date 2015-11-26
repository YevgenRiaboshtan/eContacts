package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefLessEq;

public class LessThanOrEqualVisitor extends AbstractVisitor<FilterDefLessEq>{

	protected LessThanOrEqualVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefLessEq filter) {
		predicate = cbCtx.getCriteriaBuilder().le(
				getPath(filter.getFieldName()),
				cbCtx.createFindParam(filter.getValue()));
	}

}
