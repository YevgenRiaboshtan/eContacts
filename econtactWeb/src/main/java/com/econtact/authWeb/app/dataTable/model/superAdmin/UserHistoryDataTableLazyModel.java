package com.econtact.authWeb.app.dataTable.model.superAdmin;

import java.math.BigDecimal;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.authWeb.app.helpers.FilterHelper;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AccountUserAudView;

public class UserHistoryDataTableLazyModel extends AbstractGenericDataTableModel<AccountUserAudView> {
	private static final long serialVersionUID = 1995987410291483802L;

	private final BigDecimal idUser;
	
	public UserHistoryDataTableLazyModel(DataTable table, FilterHelper filterHelper, String idUser) {
		super(table, filterHelper);
		this.idUser = BigDecimal.valueOf(Long.parseLong(idUser));
	}

	@Override
	protected SearchCriteria<AccountUserAudView> createQueries() {
		SearchCriteria<AccountUserAudView> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccountUserAudView.class));
		criteria.andFilter(new FilterDefEquals(EntityHelper.ID_A, idUser));
		return criteria;
	}

}
