package com.econtact.dataModel.data.filter.visitor;

import javax.persistence.criteria.CriteriaBuilder;

import com.econtact.dataModel.data.filter.FilterDefEndsWith;

public class EndsWithVisitor extends AbstractVisitor<FilterDefEndsWith> {

	protected EndsWithVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefEndsWith filter) {
        final StringBuilder builder = new StringBuilder();
        if (!filter.getValue().startsWith(LIKE_ANY_CHARS)) {
        	builder.append(LIKE_ANY_CHARS);
        }
        builder.append(filter.getValue());
        final CriteriaBuilder cb = cbCtx.getCriteriaBuilder();
        predicate = cb.like(getPath(filter.getFieldName()), cbCtx.createFindParam(builder.toString()));
    }

}
