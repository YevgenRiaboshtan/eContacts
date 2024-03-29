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
            case LESS_THAN:
            	return new LessThanVisitor(ctx);
            case LESS_THAN_OR_EQUALS:
            	return new LessThanOrEqualVisitor(ctx);
            case GREATER_THAN:
            	return new GreaterThanVisitor(ctx);
            case GREATER_THAN_OR_EQUALS:
            	return new GreaterThanOrEqualVisitor(ctx);
            case IN_LIST:
            	return new InListVisitor(ctx);
            case NOT_IN_LIST:
            	return new NotInListVisitor(ctx);
            case EQUALS_TWO_FIELDS:
            	return new EqualsTwoFieldsVisitor(ctx);
            case NOT_EQUALS_TWO_FIELDS:
            	return new NotEqualsTwoFieldsVisitor(ctx);
            default:
                throw new UnsupportedOperationException("Filter with type :'"
                        + filterDefEnum + "' not supported. Fix me");
        }
    }

}
