package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefGreater;

public class GreaterThanVisitor extends AbstractVisitor<FilterDefGreater> {

	GreaterThanVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefGreater filter) {
		predicate = cbCtx.getCriteriaBuilder().gt(
				getPath(filter.getFieldName()),
				cbCtx.createFindParam(filter.getValue()));
	}

}
