package org.auth.dataModel.data.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.auth.dataModel.data.filter.AbstractFilterDef;
import org.auth.dataModel.data.filter.FilterDefAnd;

public class SearchCriteria<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Queries<T> queries;
	private final List<SortingInfo> sortingInfos = new ArrayList<SortingInfo>();
	private AbstractFilterDef filter;
	private final Map<String, String> hints = new HashMap<String, String>();
	
	public SearchCriteria(final Queries<T> queries) {
		this.queries = queries;
	}
	
	public TypedQuery<T> getSelectQuery(final EntityManager em) {
		final TypedQuery<T> result = queries.getSelectQuery(em, filter, sortingInfos);
		for (Map.Entry<String, String> hint : hints.entrySet()) {
			result.setHint(hint.getKey(), hint.getValue());
		}
		return result;
	}
	
	public TypedQuery<Long> getRowCountQuery(final EntityManager em) {
		final TypedQuery<Long> result = queries.getRowcountQuery(em, filter);
		for (Map.Entry<String, String> hint : hints.entrySet()) {
			result.setHint(hint.getKey(), hint.getValue());
		}
		return result;
	}
	
	public SearchCriteria andFilter(AbstractFilterDef filter) {
		if (filter != null && !filter.isEmpty()) {
			if (this.filter == null) {
				this.filter = filter;
			} else {
				this.filter = new FilterDefAnd(this.filter, filter);
			}
		}
		return this;
	}
	
	public AbstractFilterDef getFilter() {
		return filter;
	}
	
	public SearchCriteria<T> addSortingInfo(final String columnName, final boolean ascending) {
		final SortingInfo sortingInfo = new SortingInfo(columnName, ascending);
		if (sortingInfos.contains(sortingInfo)) {
			//for developers only
			throw new IllegalArgumentException(sortingInfo + " is defined in search criteria");
		}
		sortingInfos.add(sortingInfo);
		return this;
	}
	
	public SearchCriteria<T> addHint(String name, String value) {
		hints.put(name, value);
		return this;
	}
	
	public Map<String, String> getHints() {
		return hints;
	}
}
