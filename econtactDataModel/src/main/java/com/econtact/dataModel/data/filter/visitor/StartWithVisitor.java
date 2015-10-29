package com.econtact.dataModel.data.filter.visitor;

import javax.persistence.criteria.CriteriaBuilder;

import com.econtact.dataModel.data.filter.FilterDefStartsWith;

public class StartWithVisitor extends AbstractVisitor<FilterDefStartsWith> {

	protected StartWithVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefStartsWith filter) {
        final StringBuilder builder = new StringBuilder();
        builder.append(filter.getValue());
        if (!filter.getValue().endsWith(LIKE_ANY_CHARS)) {
            builder.append(LIKE_ANY_CHARS);
        }
        final CriteriaBuilder cb = cbCtx.getCriteriaBuilder();
        predicate = cb.like(getPath(filter.getFieldName()), cbCtx.createFindParam(builder.toString()));
    }

}
