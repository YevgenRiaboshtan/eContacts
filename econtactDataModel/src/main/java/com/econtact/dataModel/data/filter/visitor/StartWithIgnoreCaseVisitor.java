package com.econtact.dataModel.data.filter.visitor;

import javax.persistence.criteria.CriteriaBuilder;

import com.econtact.dataModel.data.filter.FilterDefStartsWithIgnoreCase;

public class StartWithIgnoreCaseVisitor extends AbstractVisitor<FilterDefStartsWithIgnoreCase> {

	StartWithIgnoreCaseVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefStartsWithIgnoreCase filter) {
        final StringBuilder builder = new StringBuilder();
        builder.append(filter.getValue().toUpperCase());
        if (!filter.getValue().endsWith(LIKE_ANY_CHARS)) {
            builder.append(LIKE_ANY_CHARS);
        }
        final CriteriaBuilder cb = cbCtx.getCriteriaBuilder();
        predicate = cb.like(cb.upper(getPath(filter.getFieldName())), cbCtx.createFindParam(builder.toString()));
    }

}
