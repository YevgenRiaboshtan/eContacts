package com.econtact.dataModel.data.query;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.econtact.dataModel.data.filter.Filter;

public interface Queries<T> extends Serializable {

	TypedQuery<Long> getRowcountQuery(EntityManager em, Filter filter);
	
	TypedQuery<T> getSelectQuery(EntityManager em, Filter filter, List<SortingInfo> sortingInfos);
}