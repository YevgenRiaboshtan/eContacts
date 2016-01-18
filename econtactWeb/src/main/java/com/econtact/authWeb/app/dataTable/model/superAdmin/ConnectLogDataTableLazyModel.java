package com.econtact.authWeb.app.dataTable.model.superAdmin;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.model.entity.common.ConnectAuditEntity;

public class ConnectLogDataTableLazyModel extends AbstractGenericDataTableModel<ConnectAuditEntity> {
	private static final long serialVersionUID = -2962493177067837052L;

	public ConnectLogDataTableLazyModel(DataTable table, UserContext userContext) {
		super(table, userContext);
	}

	@Override
	protected SearchCriteria<ConnectAuditEntity> createQueries() {
		SearchCriteria<ConnectAuditEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(ConnectAuditEntity.class));
		return criteria;
	}
}
