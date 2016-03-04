package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefEqualsTwoFields;

public class EqualsTwoFieldsVisitor extends AbstractVisitor<FilterDefEqualsTwoFields> {

	protected EqualsTwoFieldsVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefEqualsTwoFields filter) {
		predicate = cbCtx.getCriteriaBuilder().equal(getPath(filter.getFieldName()), getPath(filter.getAnotherField()));
	}
}
