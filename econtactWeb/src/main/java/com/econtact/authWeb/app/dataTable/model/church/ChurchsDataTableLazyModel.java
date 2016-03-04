package com.econtact.authWeb.app.dataTable.model.church;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.query.church.ChurchsQueries;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public class ChurchsDataTableLazyModel extends AbstractGenericDataTableModel<ChurchEntity> {
	private static final long serialVersionUID = -7111653076618751192L;

	public ChurchsDataTableLazyModel(DataTable table, UserContext userContext) {
		super(table, userContext);
	}

	@Override
	protected SearchCriteria<ChurchEntity> createQueries() {
		SearchCriteria<ChurchEntity> criteria = new SearchCriteria<>(new ChurchsQueries(getUserContext().getUser()));
		return criteria;
	}

}
