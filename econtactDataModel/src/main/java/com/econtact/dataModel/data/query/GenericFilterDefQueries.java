package com.econtact.dataModel.data.query;

import com.econtact.dataModel.model.AbstractView;

public class GenericFilterDefQueries<T extends AbstractView> extends AbstractFilterDefQueries<T> {
	private static final long serialVersionUID = 7433577849977626484L;
	
	private final Class<T> viewClass;

	public GenericFilterDefQueries(Class<T> viewClass) {
		this.viewClass = viewClass;
	}

	@Override
	protected Class<T> getViewClass() {
		return viewClass;
	}
}
