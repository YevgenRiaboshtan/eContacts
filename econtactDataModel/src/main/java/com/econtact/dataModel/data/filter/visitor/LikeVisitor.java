package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefLike;

public class LikeVisitor extends AbstractVisitor<FilterDefLike> {

	protected LikeVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefLike filter) {
        final StringBuilder builder = new StringBuilder();
        if (!filter.getValue().startsWith(LIKE_ANY_CHARS)) {
            builder.append(LIKE_ANY_CHARS);
        }
        builder.append(filter.getValue());
        if (!filter.getValue().endsWith(LIKE_ANY_CHARS)) {
            builder.append(LIKE_ANY_CHARS);
        }
        predicate = cbCtx.getCriteriaBuilder()
                .like(getPath(filter.getFieldName()), cbCtx.createFindParam(builder.toString()));
    }

}
