package com.econtact.authWeb.app.dataTable.model.quickStart;

import com.econtact.authWeb.app.view.AbstractGenericDataTableModel;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;

public class AdminDataTableLazyModel extends AbstractGenericDataTableModel<AdvanceUserEntity> {
	private static final long serialVersionUID = -1009954726450744039L;

	@Override
	protected SearchCriteria<AdvanceUserEntity> createQueries() {
		SearchCriteria<AdvanceUserEntity> criteria =  new SearchCriteria<>(new GenericFilterDefQueries<>(AdvanceUserEntity.class));
		criteria.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
		return criteria;
	}
}
