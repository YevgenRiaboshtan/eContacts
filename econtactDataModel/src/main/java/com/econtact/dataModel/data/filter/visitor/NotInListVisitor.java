package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefNotInList;

public class NotInListVisitor extends AbstractVisitor<FilterDefNotInList> {

	NotInListVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefNotInList filter) {
		if (!filter.getValue().isEmpty()) {
			predicate = cbCtx.getCriteriaBuilder()
					.not(cbCtx.getRoot().get(filter.getFieldName()).in(cbCtx.createFindParam(filter.getValue())));
		}
	}

}
