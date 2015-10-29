package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefNotNull;

public class NotNullVisitor extends AbstractVisitor<FilterDefNotNull>{

	NotNullVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefNotNull filter) {
		predicate = getPath(filter.getFieldName()).isNotNull();
	}

}
