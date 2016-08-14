package com.econtact.dataModel.data.filter.visitor;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.econtact.dataModel.data.filter.FilterDefGreater;

public class GreaterThanVisitor extends AbstractVisitor<FilterDefGreater> {

	GreaterThanVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefGreater filter) {
		if (filter.getValue() instanceof Date) {
			predicate = cbCtx.getCriteriaBuilder()
					.greaterThan(
							getPath(filter.getFieldName()), 
							DateUtils.addDays((Date) filter.getValue(), 1));
		} else {
			predicate = cbCtx.getCriteriaBuilder()
					.gt(
						getPath(filter.getFieldName()),
						cbCtx.createFindParam(filter.getValue()));
		}
	}

}
