package com.econtact.authWeb.app.dataTable.model.church;

import org.primefaces.component.datatable.DataTable;

import com.econtact.authWeb.app.dataTable.model.AbstractGenericDataTableModel;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.query.group.GroupsQueries;
import com.econtact.dataModel.model.entity.church.GroupEntity;

public class GroupsDataTableLazyModel extends AbstractGenericDataTableModel<GroupEntity>{
	private static final long serialVersionUID = -5496892015231164115L;

	public GroupsDataTableLazyModel(DataTable table, UserContext userContext) {
		super(table, userContext);
	}

	@Override
	protected SearchCriteria<GroupEntity> createQueries() {
		SearchCriteria<GroupEntity> criteria = new  SearchCriteria<>(new GroupsQueries(getUserContext().getUser()));
		return criteria;
	}
}
