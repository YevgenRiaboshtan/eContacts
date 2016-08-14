package com.econtact.dataModel.data.filter.visitor;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.econtact.dataModel.data.filter.FilterDefLessEq;

public class LessThanOrEqualVisitor extends AbstractVisitor<FilterDefLessEq>{

	protected LessThanOrEqualVisitor(VisitorContext cbCtx) {
		super(cbCtx);
	}

	@Override
	public void processFilter(FilterDefLessEq filter) {
		if (filter.getValue() instanceof Date) {
			predicate = cbCtx.getCriteriaBuilder()
					.lessThanOrEqualTo(
							getPath(filter.getFieldName()), 
							cbCtx.createFindParam(
									DateUtils.addDays((Date)filter.getValue(), 1)));
		} else {
			predicate = cbCtx.getCriteriaBuilder()
				.le(getPath(filter.getFieldName()), cbCtx.createFindParam(filter.getValue()));
		}
	}

}
