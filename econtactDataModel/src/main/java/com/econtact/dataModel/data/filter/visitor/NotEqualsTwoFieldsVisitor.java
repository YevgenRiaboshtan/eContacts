package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefNotEqualsTwoFields;

public class NotEqualsTwoFieldsVisitor extends AbstractVisitor<FilterDefNotEqualsTwoFields> {

	NotEqualsTwoFieldsVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefNotEqualsTwoFields filter) {
		predicate = cbCtx.getCriteriaBuilder().notEqual(getPath(filter.getFieldName()),
				getPath(filter.getAnotherField()));
	}
}
