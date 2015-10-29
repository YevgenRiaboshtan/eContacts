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

import com.econtact.authWeb.app.helpers.FilterHelper;
import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.filter.AbstractFilterDef;
import com.econtact.dataModel.data.filter.FilterDataTypeEnum;
import com.econtact.dataModel.data.filter.FilterDefAnd;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.query.SortingInfo;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.AbstractEntity;

public abstract class AbstractGenericDataTableModel<T extends AbstractView> extends LazyDataModel<T> {
	private static final long serialVersionUID = 2966276343006226214L;
	private static final String FILTER_TYPE_ATTR = "filterDataType"; 
	private static final String FILTER_FIELD_ATTR = "filterField";
	
	private FilterHelper filterHelper;
	
	private Map<String, T> cache = new HashMap<String, T>();
	private Map<String, FilterDataTypeEnum> filterDataTypes = new HashMap<>();
	
	public AbstractGenericDataTableModel(DataTable table, FilterHelper filterHelper) {
		super();
		this.filterHelper = filterHelper;
		for (UIColumn column : table.getColumns()) {
			if (((Column) column).getAttributes().containsKey(FILTER_TYPE_ATTR)
					&& ((Column) column).getAttributes().containsKey(FILTER_FIELD_ATTR)) {
				filterDataTypes.put(
						((Column) column).getAttributes().get(FILTER_FIELD_ATTR).toString(),
						filterHelper.parseDataTypeString(((Column) column).getAttributes().get(FILTER_TYPE_ATTR).toString()));
			}
		}
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
	
	protected AbstractFilterDef makeFilters(Map<String, Object> filters) {
		FilterDefAnd result = new FilterDefAnd();
		for (Entry<String, Object> entry : filters.entrySet()) {
			result.addDefinition(filterHelper.getMakeFilter(filterDataTypes.get(entry.getKey()), entry.getKey(), entry.getValue()));
		}
		return result;
	}

	protected abstract SearchCriteria<T> createQueries();
}
