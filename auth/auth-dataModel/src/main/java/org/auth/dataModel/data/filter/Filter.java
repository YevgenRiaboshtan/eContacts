package org.auth.dataModel.data.filter;

import java.io.Serializable;

public interface Filter<E> extends Serializable {

	FilterDefEnum getType();

    E getValue();

    String getFieldName();

    boolean isEmpty();

    <T extends Filter> void createFindCriteria(FindCriteriaVisitor<T> visitor);
}
