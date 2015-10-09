package com.econtact.authWeb.app.dataTable.model.quickStart;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.filter.FilterDefLike;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.ActiveStatusEnum;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;

public class AdminDataTableLazyModel extends LazyDataModel<AdvanceUserEntity>{
	private static final long serialVersionUID = -1009954726450744039L;
	
	public AdminDataTableLazyModel(DataTable dataTable) {
		super();
		for(UIColumn column : dataTable.getColumns()) {
			System.out.println(column.getFilterBy());
		}
	}
	
	@Override
	public Object getRowKey(AdvanceUserEntity entity) {
		return entity.getId();
	}
	
	@Override
	public List<AdvanceUserEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		SearchCriteria<AdvanceUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AdvanceUserEntity.class));
		criteria.andFilter(new FilterDefEquals("roles.role", RoleType.ROLE_ADMIN));
		if (StringUtils.isNotBlank(sortField) 
				&& sortOrder != null
				&& !SortOrder.UNSORTED.equals(sortOrder)){
			criteria.addSortingInfo(sortField, SortOrder.ASCENDING.equals(sortOrder));	
		}
		
		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			if ("enabledAccount".equals(entry.getKey())) {
				ActiveStatusEnum value = null;
				if (StringUtils.equalsIgnoreCase("true", entry.getValue().toString()))
					value = ActiveStatusEnum.ENABLE;
				if (StringUtils.equalsIgnoreCase("false", entry.getValue().toString()))
					value = ActiveStatusEnum.DISABLE;
				if ( value != null)
					criteria.andFilter(new FilterDefEquals(entry.getKey(), value));
			} else {
				criteria.andFilter(new FilterDefLike(entry.getKey(), (String) entry.getValue()));
			}
		}
		List<AdvanceUserEntity> data = WebHelper.getBean(GenericService.class).find(criteria, first, pageSize);
		this.setRowCount(WebHelper.getBean(GenericService.class).getRowCount(criteria).intValue());
		return data;
	}
}
