package com.econtact.dataModel.data.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.econtact.dataModel.data.filter.Filter;
import com.econtact.dataModel.data.filter.visitor.AbstractVisitor;
import com.econtact.dataModel.data.filter.visitor.VisitorContext;
import com.econtact.dataModel.data.filter.visitor.VisitorFactory;
import com.econtact.dataModel.model.AbstractView;

public abstract class AbstractFilterDefQueries<T extends AbstractView> implements Queries<T> {
	private static final long serialVersionUID = 1L;

	protected abstract Class<T> getViewClass();
	
	protected Predicate getAdditionalPredicate(VisitorContext visitorCtx, CriteriaQuery cq) {
		return null;
	}
	
	protected void prepareSelectQuery(final VisitorContext visitorCtx) {
	}
	
	protected void updateSelectQuery(final VisitorContext visitorCtx, final CriteriaQuery cq) {
	}
	
	protected void addSortInfo(final VisitorContext visitorCtx, final CriteriaQuery cq,
			final List<SortingInfo> sortingInfos) {
		final List<Order> orders = new ArrayList();
		for (SortingInfo sortingInfo : sortingInfos) {
			From parent = visitorCtx.getRoot();
			final String[] names = sortingInfo.getColumnName().split("\\.");
			From result;
			for (int index = 0; index < names.length - 1; index++) {
				final String name = names[index];
				result = visitorCtx.getFromMap().get(name);
				if (result == null) {
					result = parent.join(name, JoinType.LEFT);
					visitorCtx.getFromMap().put(name, result);
				}
				parent = result;
			}
			orders.add(new OrderImpl(parent.get(names[names.length - 1]), sortingInfo.isAscending()));
		}
		if (!orders.isEmpty()) {
			cq.orderBy(orders);
		}
	}
	
	@Override
	public TypedQuery<T> getSelectQuery(EntityManager em, Filter filter, List<SortingInfo> sortingInfos) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<T> cq = cb.createQuery(getViewClass());
		final Root root = cq.from(getViewClass());
		final VisitorContext visitorCtx = new VisitorContext(cb, root);
		prepareSelectQuery(visitorCtx);
		createWhereClause(visitorCtx, cq, filter);
		addSortInfo(visitorCtx, cq, sortingInfos);
		updateSelectQuery(visitorCtx, cq);
		final TypedQuery<T> result = em.createQuery(cq);
		for (Map.Entry<String, Object> entry : visitorCtx.getParams().entrySet()) {
			result.setParameter(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	@Override
	public TypedQuery<Long> getRowCountQuery(EntityManager em, Filter filter) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root root = cq.from(getViewClass());
		final VisitorContext visitorCtx = new VisitorContext(cb, root);
		createWhereClause(visitorCtx, cq, filter);
		cq.select(cb.count(root));
		final TypedQuery<Long> result = em.createQuery(cq);
		for (Map.Entry<String, Object> entry : visitorCtx.getParams().entrySet()) {
			result.setParameter(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	private void createWhereClause(final VisitorContext visitorCtx, final CriteriaQuery cq, Filter filter) {
		Predicate predicate = null;
		if (filter != null) {
			final AbstractVisitor filterVisitor = VisitorFactory.getVisitor(filter.getType(), visitorCtx);
			filter.createFindCriteria(filterVisitor);
			predicate = filterVisitor.getPredicate();
		}
		final Predicate addPredicate = getAdditionalPredicate(visitorCtx, cq);
		if (addPredicate != null) {
			if (predicate != null) {
				predicate = visitorCtx.getCriteriaBuilder().and(addPredicate, predicate);
			} else {
				predicate = addPredicate;
			}
		}
		if (predicate != null) {
			cq.where(predicate);
		}
	}
}
