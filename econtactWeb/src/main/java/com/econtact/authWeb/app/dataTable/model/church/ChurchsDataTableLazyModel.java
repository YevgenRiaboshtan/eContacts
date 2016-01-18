package com.econtact.authWeb.app.dataTable.model.church;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.authWeb.app.helpers.FilterHelper;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public class ChurchsDataTableLazyModel extends AbstractGenericDataTableModel<ChurchEntity> {
	private static final long serialVersionUID = -7111653076618751192L;

	public ChurchsDataTableLazyModel(DataTable table, FilterHelper filterHelper) {
		super(table, filterHelper);
	}

	@Override
	protected SearchCriteria<ChurchEntity> createQueries() {
		SearchCriteria<ChurchEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(ChurchEntity.class));
		criteria.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
		return criteria;
	}

}
