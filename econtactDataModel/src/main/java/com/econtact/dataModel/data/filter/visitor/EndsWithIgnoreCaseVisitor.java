package com.econtact.dataModel.data.filter.visitor;

import javax.persistence.criteria.CriteriaBuilder;

import com.econtact.dataModel.data.filter.FilterDefEndsWithIgnoreCase;

public class EndsWithIgnoreCaseVisitor extends AbstractVisitor<FilterDefEndsWithIgnoreCase> {

	EndsWithIgnoreCaseVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefEndsWithIgnoreCase filter) {
        final StringBuilder builder = new StringBuilder();
        if (!filter.getValue().startsWith(LIKE_ANY_CHARS)) {
            builder.append(LIKE_ANY_CHARS);
        }
        builder.append(filter.getValue().toUpperCase());
        final CriteriaBuilder cb = cbCtx.getCriteriaBuilder();
        predicate = cb.like(cb.upper(getPath(filter.getFieldName())), cbCtx.createFindParam(builder.toString()));
    }

}
