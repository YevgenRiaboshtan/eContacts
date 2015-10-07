package com.econtact.dataModel.data.filter.visitor;

import javax.persistence.criteria.CriteriaBuilder;

import com.econtact.dataModel.data.filter.FilterDefEquals;

public class EqualsVisitor extends AbstractVisitor<FilterDefEquals> {
	EqualsVisitor(final VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefEquals filter) {
		final CriteriaBuilder cb = cbCtx.getCriteriaBuilder();
		if (filter.getValue() instanceof Number) { // FIXME expands filter data types
			predicate = cb.equal(getPath(filter.getFieldName()), filter.getValue());
		} /*else if (filter.getValue() instanceof Date) {
			Date startDate = DateUtils.truncate((Date) filter.getValue(), Calendar.DATE);
			predicate = cb.between(getPath(filter.getFieldName()), cbCtx.createFindParam(startDate),
					cbCtx.createFindParam(DateUtils.addDays(startDate, 1)));
		}*/ else {
			predicate = cb.equal(getPath(filter.getFieldName()), cbCtx.createFindParam(filter.getValue()));
		}
	}
}