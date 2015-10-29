package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefEnum;

public class VisitorFactory {

    public static AbstractVisitor getVisitor(final FilterDefEnum filterDefEnum, VisitorContext ctx) {
        switch(filterDefEnum) {
            case AND:
                return new AndVisitor(ctx);
            case OR:
                return new OrVisitor(ctx);
            case EQUALS:
                return new EqualsVisitor(ctx);
            case NOT_EQUALS:
                return new NotEqualVisitor(ctx);
            case EQUALS_IGNORE_CASE:
            	return new EqualsIgnoreCaseVisitor(ctx);
            case LIKE:
            	return new LikeVisitor(ctx);
            case  STARTS_WITH:
            	return new StartWithVisitor(ctx);
            case STARTS_WITH_IGNORE_CASE:
            	return new StartWithIgnoreCaseVisitor(ctx);
            case IS_NULL:
            	return new IsNullVisitor(ctx);
            case NOT_NULL:
            	return new NotNullVisitor(ctx);
            case ENDS_WITH:
            	return new EndsWithVisitor(ctx);
            case ENDS_WITH_IGNORE_CASE:
            	return new EndsWithIgnoreCaseVisitor(ctx);
            default:
                throw new UnsupportedOperationException("Filter with type :'"
                        + filterDefEnum + "' not supported. Fix me");
        }
    }

}
