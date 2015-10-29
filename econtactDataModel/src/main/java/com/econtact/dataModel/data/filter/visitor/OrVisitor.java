package com.econtact.dataModel.data.filter.visitor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import com.econtact.dataModel.data.filter.AbstractFilterDef;
import com.econtact.dataModel.data.filter.FilterDefOr;

public class OrVisitor extends AbstractVisitor<FilterDefOr> {

    private final List<Predicate> predicates = new ArrayList<>();

    OrVisitor(VisitorContext cbCtx) {
        super(cbCtx);
    }

    @Override
    public Predicate getPredicate() {
        if (predicates.isEmpty()) {
            return null;
        } else if (predicates.size() == 1) {
            return predicates.get(0);
        } else {
            return cbCtx.getCriteriaBuilder().or(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    @Override
    public void processFilter(FilterDefOr orFilter) {
        final List<AbstractFilterDef> filters = orFilter.getFilterDefs();
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
