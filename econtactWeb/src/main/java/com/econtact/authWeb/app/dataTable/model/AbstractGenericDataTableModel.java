package com.econtact.authWeb.app.dataTable.model;

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

import com.econtact.authWeb.app.utils.FilterUtils;
import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.context.UserContext;
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
	
	private final UserContext userContext;
	
	private Map<String, T> cache = new HashMap<String, T>();
	private Map<String, FilterDataTypeEnum> filterDataTypes = new HashMap<>();
	
	public AbstractGenericDataTableModel(DataTable table, UserContext userContext) {
		super();
		this.userContext = userContext;
		for (UIColumn column : table.getColumns()) {
			if (((Column) column).getAttributes().containsKey(FILTER_TYPE_ATTR)
					&& ((Column) column).getAttributes().containsKey(FILTER_FIELD_ATTR)) {
				filterDataTypes.put(
						((Column) column).getAttributes().get(FILTER_FIELD_ATTR).toString(),
						parseDataTypeString(((Column) column).getAttributes().get(FILTER_TYPE_ATTR).toString()));
			}
		}
	}
	
	public <E extends AbstractEntity> void removeEntity(E entity) {
		WebUtils.getBean(GenericService.class).remove(entity, userContext);
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
		for (SortingInfo item : orders) {
			criteria.addSortingInfo(item.getColumnName(), item.isAscending());
		}
		List<T> result = WebUtils.getBean(GenericService.class).find(criteria, first, pageSize);
		cache.clear();
		for (T item : result) {
			cache.put(getRowKey(item).toString(), item);
		}
		this.setRowCount(WebUtils.getBean(GenericService.class).getRowCount(criteria).intValue());
		return result;
	}
	
	private FilterDataTypeEnum parseDataTypeString(String dataType) {
		switch (dataType) {
		case "TEXT":
			return FilterDataTypeEnum.TEXT;
		case "DATE":
			return FilterDataTypeEnum.DATE;
		case "LONG":
			return FilterDataTypeEnum.LONG;
		case "NUMBER":
			return FilterDataTypeEnum.NUMBER;
		case "ENUM":
			return FilterDataTypeEnum.ENUM;
		case "DICTIONARY":
			return FilterDataTypeEnum.DICTIONARY;
		case "BOOLEAN":
			return FilterDataTypeEnum.BOOLEAN;
		case "NONE":
			return FilterDataTypeEnum.NONE;
		default:
			return null;
		}
	}
	protected AbstractFilterDef makeFilters(Map<String, Object> filters) {
		FilterDefAnd result = new FilterDefAnd();
		for (Entry<String, Object> entry : filters.entrySet()) {
			result.addDefinition(FilterUtils.getMakeFilter(filterDataTypes.get(entry.getKey()), entry.getKey(), entry.getValue()));
		}
		return result;
	}

	protected abstract SearchCriteria<T> createQueries();
}
