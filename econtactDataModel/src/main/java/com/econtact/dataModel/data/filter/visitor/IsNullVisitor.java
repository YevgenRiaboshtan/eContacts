package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefIsNull;

public class IsNullVisitor extends AbstractVisitor<FilterDefIsNull>{

	IsNullVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefIsNull filter) {
		predicate = getPath(filter.getFieldName()).isNull();
	}

}
