package com.econtact.dataModel.data.filter.visitor;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.econtact.dataModel.data.filter.AbstractFilterDef;
import com.econtact.dataModel.data.filter.FindCriteriaVisitor;

public abstract class AbstractVisitor<T extends AbstractFilterDef> implements FindCriteriaVisitor<T> {

	/**
	 * Escape char will be used in like expression.
	 */
	protected static final char LIKE_ESCAPE_CHAR = '\\';
	protected static final String LIKE_ANY_CHARS = "%";

	protected final VisitorContext cbCtx;
	protected Predicate predicate;

	public Predicate getPredicate() {
		return predicate;
	}

	protected AbstractVisitor(final VisitorContext cbCtx) {
		this.cbCtx = cbCtx;
	}

	protected Path getPath(final String fieldName) {
		From parent = cbCtx.getRoot();
		final String[] names = fieldName.split("\\.");
		From result;
		for (int index = 0; index < names.length - 1; index++) {
			final String name = names[index];
			result = cbCtx.getFromMap().get(name);
			if (result == null) {
				result = parent.join(name, JoinType.LEFT);
				cbCtx.getFromMap().put(name, result);
			}
			parent = result;
		}
		return parent.get(names[names.length - 1]);
	}
}
