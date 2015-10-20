package com.econtact.authWeb.app.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.filter.AbstractFilterDef;
import com.econtact.dataModel.data.filter.FilterDefAnd;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.query.SortingInfo;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.accout.ActiveStatusEnum;

public abstract class AbstractGenericDataTableModel<T extends AbstractView> extends LazyDataModel<T> {
	private static final long serialVersionUID = 2966276343006226214L;

	private Map<String, T> cache = new HashMap<String, T>();
	
	public AbstractGenericDataTableModel() {
		super();
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
		if (ActiveStatusEnum.DISABLE.toString().equals(value)) {
			return new FilterDefEquals(field, ActiveStatusEnum.DISABLE);
		} else if (ActiveStatusEnum.ENABLE.toString().equals(value)) {
			return new FilterDefEquals(field, ActiveStatusEnum.ENABLE);
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
