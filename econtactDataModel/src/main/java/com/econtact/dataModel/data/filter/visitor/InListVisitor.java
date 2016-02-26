package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefInList;

public class InListVisitor extends AbstractVisitor<FilterDefInList>{

	InListVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefInList filter) {
        if (!filter.getValue().isEmpty()) {
            predicate = getPath(filter.getFieldName()).in(filter.getValue());
        }
    }
}
