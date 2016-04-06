package com.econtact.dataModel.data.filter.visitor;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;

import org.apache.commons.lang.time.DateUtils;

import com.econtact.dataModel.data.filter.FilterDefEquals;

public class EqualsVisitor extends AbstractVisitor<FilterDefEquals> {
	EqualsVisitor(final VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefEquals filter) {
		final CriteriaBuilder cb = cbCtx.getCriteriaBuilder();
		if (filter.getValue() instanceof Date) {
			Date startDate = DateUtils.truncate((Date)filter.getValue(), Calendar.DATE);
        	predicate = cb.and(
        			cb.greaterThanOrEqualTo(getPath(filter.getFieldName()), cbCtx.createFindParam(startDate)),
        			cb.lessThan(getPath(filter.getFieldName()), cbCtx.createFindParam(DateUtils.addDays(startDate, 1))));
		} else {
			predicate = cb.equal(getPath(filter.getFieldName()), cbCtx.createFindParam(filter.getValue()));
		}
	}
}
