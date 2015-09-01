package com.econtact.dataModel.data.query;

import java.io.Serializable;

public class SortingInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private final String columnName;
    
    private final boolean asc;

    SortingInfo(String columnName, boolean asc) {
        assert (columnName != null && !columnName.isEmpty());
        this.columnName = columnName;
        this.asc = asc;
    }

    SortingInfo(String columnName) {
        this(columnName, true);
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isAscending() {
        return asc;
    }

    @Override
    public int hashCode() {
        return columnName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SortingInfo other = (SortingInfo) obj;
        return columnName.equals(other.columnName);
    }

    @Override
    public String toString() {
        return new StringBuilder(SortingInfo.class.getSimpleName()).append(": columnName=").append(columnName)
                .append(", asc=").append(asc).toString();
    }

    public static SortingInfo create(final String attrName, boolean asc) {
        return new SortingInfo(attrName, asc);
    }
}
