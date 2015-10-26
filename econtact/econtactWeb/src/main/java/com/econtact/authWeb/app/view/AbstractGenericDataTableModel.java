package com.econtact.authWeb.app.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.filter.AbstractFilterDef;
import com.econtact.dataModel.data.filter.FilterDefAnd;
import com.econtact.dataModel.data.filter.FilterDefEnum;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.query.SortingInfo;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

public abstract class AbstractGenericDataTableModel<T extends AbstractView> extends LazyDataModel<T> {
	private static final long serialVersionUID = 2966276343006226214L;

	private Map<String, T> cache = new HashMap<String, T>();
	private FilterDefEnum defaultFilterType = FilterDefEnum.STARTS_WITH_IGNORE_CASE;
	private Map<String, FilterDefEnum> filtersType = new HashMap<String, FilterDefEnum>();
	
	public AbstractGenericDataTableModel(DataTable table) {
		super();
		/*for (UIColumn column : table.getColumns()) {
			if (((Column) column).getAttributes().containsKey("filterType")) {
				
			} else {
				
			}
		}*/
	}
	
	public <E extends AbstractEntity> void removeEntity(E entity) {
		WebHelper.getBean(GenericService.class).remove(entity, WebHelper.getUserContext());
	}
	
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		final List<SortingInfo> sorting = new ArrayList<SortingInfo>();
		if (StringUtils.isNotBlank(sortField)) {
			sorting.add(new SortingInfo(sortField, sortOrder.equals(SortOrder.ASCENDING)));
		}
		return find(first, pageSize, sorting, filters);
	}

	@Override
	public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		final List<SortingInfo> sorting = new ArrayList<SortingInfo>();
		for (SortMeta item : multiSortMeta) {
			sorting.add(new SortingInfo(item.getSortField(), item.getSortOrder().equals(SortOrder.ASCENDING)));
		}
		return find(first, pageSize, sorting, filters);
	}
	
	@Override
	public Object getRowKey(T object) {
        return object.getId();
    }
	
	public T getRowData(String rowKey) {
		T result = cache.get(rowKey);
		if (result == null) {
			throw new IllegalArgumentException("Not find items in result");
		}
		return result;
	}
	
	private List<T> find(int first, int pageSize, List<SortingInfo> orders, Map<String, Object> filters) {
		SearchCriteria<T> criteria = createQueries();
		criteria.andFilter(makeFilters(filters));
		criteria.clearSortingInfo();
		for (SortingInfo item : orders) {
			criteria.addSortingInfo(item.getColumnName(), item.isAscending());
		}
		List<T> result = WebHelper.getBean(GenericService.class).find(criteria, first, pageSize);
		cache.clear();
		for (T item : result) {
			cache.put(getRowKey(item).toString(), item);
		}
		this.setRowCount(WebHelper.getBean(GenericService.class).getRowCount(criteria).intValue());
		return result;
	}
	
	//TODO default create equals filter
	private AbstractFilterDef createFilterByValue(String field, Object value) {
		if (UserStatusEnum.DISABLE.toString().equals(value)) {
			return new FilterDefEquals(field, UserStatusEnum.DISABLE);
		} else if (UserStatusEnum.ENABLE.toString().equals(value)) {
			return new FilterDefEquals(field, UserStatusEnum.ENABLE);
		}
		return new FilterDefEquals(field, value);
	}
	
	protected AbstractFilterDef makeFilters(Map<String, Object> filters) {
		FilterDefAnd result = new FilterDefAnd();
		for (Entry<String, Object> entry : filters.entrySet()) {
			System.out.println(entry.getKey());
			result.addDefinition(createFilterByValue(entry.getKey(), entry.getValue()));
		}
		return result;
	}

	protected abstract SearchCriteria<T> createQueries();
}
