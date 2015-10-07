package com.econtact.authWeb.app.dataTable.model.quickStart;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;

public class AdminDataTableLazyModel extends LazyDataModel<AdvanceUserEntity>{
	private static final long serialVersionUID = -1009954726450744039L;
	
	
	@Override
	public Object getRowKey(AdvanceUserEntity entity) {
		return entity.getId();
	}
	
	@Override
	public List<AdvanceUserEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		SearchCriteria<AdvanceUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AdvanceUserEntity.class));
		criteria.andFilter(new FilterDefEquals("roles.role", RoleType.ROLE_ADMIN));
		List<AdvanceUserEntity> data = WebHelper.getBean(GenericService.class).find(criteria, first, pageSize);
		this.setRowCount(WebHelper.getBean(GenericService.class).getRowCount(criteria).intValue());
		return data;
	}
}
