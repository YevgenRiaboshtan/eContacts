package com.econtact.dataModel.data.filter.visitor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import com.econtact.dataModel.data.filter.AbstractFilterDef;
import com.econtact.dataModel.data.filter.FilterDefAnd;

public class AndVisitor extends AbstractVisitor<FilterDefAnd> {

    private final List<Predicate> predicates = new ArrayList<>();

    /**
     * Should use only {@link VisitorFactory}
     * @param cbCtx
     */
    AndVisitor(final VisitorContext cbCtx) {
        super(cbCtx);
    }

    @Override
    public Predicate getPredicate() {
        if (predicates.isEmpty()) {
            return null;
        } else if (predicates.size() == 1) {
            return predicates.get(0);
        } else {
            return cbCtx.getCriteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    @Override
    public void processFilter(final FilterDefAnd andFilter) {
        final List<AbstractFilterDef> filters = andFilter.getFilterDefs();
        for (AbstractFilterDef filter : filters) {
            final AbstractVisitor visitor = VisitorFactory.getVisitor(filter.getType(), cbCtx);
            filter.createFindCriteria(visitor);
            final Predicate result = visitor.getPredicate();
            if (result != null) {
                predicates.add(result);
            }
        }
    }
}
