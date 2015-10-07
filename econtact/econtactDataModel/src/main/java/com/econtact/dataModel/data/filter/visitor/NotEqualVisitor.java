package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefNotEquals;

public class NotEqualVisitor extends AbstractVisitor<FilterDefNotEquals> {

	NotEqualVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefNotEquals filter) {
		predicate = cbCtx.getCriteriaBuilder().notEqual(getPath(filter.getFieldName()),
				cbCtx.createFindParam(filter.getValue()));
	}
}
