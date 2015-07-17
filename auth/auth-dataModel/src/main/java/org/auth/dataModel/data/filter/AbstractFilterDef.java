package org.auth.dataModel.data.filter;

public abstract class AbstractFilterDef implements Filter {

	private static final long serialVersionUID = 1L;

	private final FilterDefEnum type;

	public AbstractFilterDef(FilterDefEnum type) {
		super();
		this.type = type;
	}

	public FilterDefEnum getType() {
		return this.type;
	}

	public boolean isEmpty() {
		return false;
	}

	public void createFindCriteria(FindCriteriaVisitor visitor) {
		visitor.processFilter(this);
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public String getFieldName() {
		return null;
	}
}
