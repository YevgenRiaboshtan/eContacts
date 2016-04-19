package com.econtact.authWeb.app.dataTable.model.superAdmin;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.filter.FilterDefNotEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

public class AdminDataTableLazyModel extends AbstractGenericDataTableModel<AccountUserEntity> {
	private static final long serialVersionUID = -1009954726450744039L;
	
	public AdminDataTableLazyModel(DataTable table, UserContext userContext) {
		super(table, userContext);
	}

	@Override
	protected SearchCriteria<AccountUserEntity> createQueries() {
		SearchCriteria<AccountUserEntity> criteria =  new SearchCriteria<>(new GenericFilterDefQueries<>(AccountUserEntity.class));
		criteria.andFilter(new FilterDefNotEquals(EntityHelper.ID_A, getUserContext().getUser().getId()));
		return criteria; 
	}
}
