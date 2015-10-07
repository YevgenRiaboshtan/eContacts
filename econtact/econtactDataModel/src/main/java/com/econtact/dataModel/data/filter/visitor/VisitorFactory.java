package com.econtact.dataModel.data.filter.visitor;

import com.econtact.dataModel.data.filter.FilterDefEnum;

public class VisitorFactory {

    public static AbstractVisitor getVisitor(final FilterDefEnum filterDefEnum, VisitorContext ctx) {
        switch(filterDefEnum) {
            case AND:
                return new AndVisitor(ctx);
            case OR:
                return new OrVisitor(ctx);

            /*case CONTAINS_IGNORE_CASE:
                return new ContainsIgnoreCaseVisitor(ctx);
            case NOT_CONTAINS_IGNORE_CASE:
                return new NotContainsIgnoreCaseVisitor(ctx);
            case STARTS_WITH_IGNORE_CASE:
                return new StartWithIgnoreCaseVisitor(ctx);
            case ENDS_WITH_IGNORE_CASE:
                return new EndWithIgnoreCaseVisitor(ctx);

            case EQUALS_TWO_FIELDS:
                return new EqualsTwoFieldsVisitor(ctx);
            case NOT_EQUALS_TWO_FIELDS:
                return new NotEqualsTwoFieldsVisitor(ctx);*/
            case EQUALS:
                return new EqualsVisitor(ctx);
            case NOT_EQUALS:
                return new NotEqualVisitor(ctx);
//    SELF_EQUALS,
           /* case EQUALS_IGNORE_CASE:
                return new EqualsIgnoreCaseVisitor(ctx);*/
//    /**
//     * рівне виразу
//     */
//    EQUALS_EXPRESSION,

           /* case GREATER_THAN:
                return new GreaterThanVisitor(ctx);
            case GREATER_THAN_FOR_DATE:
                return new GreaterThanDateVisitor(ctx);
            case GREATER_THAN_OR_EQUALS:
                return new GreaterThanOrEqualVisitor(ctx);
            case GREATER_THAN_OR_EQUALS_FOR_DATE:
                return new GreaterThanOrEqualsDateVisitor(ctx);*/
//    /**
//     * поревіряє чи значення є в списку
//     */
//    IN_LIST,
//    /**
//     * поревіряє чи значення не міститься в списку
//     */
//    NOT_IN_LIST,
           /* case IN_LIST:
                return new InListVisitor(ctx);
            case NOT_IN_LIST:
                return new NotInListVisitor(ctx);

            case IS_NULL:
                return new IsNullVisitor(ctx);
            case NOT_NULL:
                return new NotNullVisitor(ctx);

            case LESS_THAN:
                return new LessThanVisitor(ctx);
            case LESS_THAN_OR_EQUALS:
                return new LessThanOrEqualVisitor(ctx);
            case LESS_THAN_FOR_DATE:
                return new LessThanDateVisitor(ctx);
            case LESS_THAN_OR_EQUALS_FOR_DATE:
                return new LessThanOrEqualDateVisitor(ctx);*/

////	/** порівняння чи одне поле є менше або рівне за друге */
////	LESS_EQUALS_TWO_FIELDS,
////
////	/** порівняння чи одне поле є менше за друге */
////	LESS_TWO_FIELDS,
          /*  case LIKE:
                return new LikeVisitor(ctx);
//
*///    /**
//     * порівняння згідно шаблону (для дат)
//     */
//    DATE_LIKE,
//
//    /**
//     * загальна кількість документів які треба розглянути
//     */
//    CW_REQUIRED_CONSIDERATION_TOTAL,
//    /**
//     * документи які треба розглянути та перебувають на контролі
//     */
//    CW_REQUIRED_CONSIDERATION_CONTROL,
//    /**
//     * документи, що надійшли
//     */
//    CW_CALENDAR_RECEIVED,
//    /**
//     * документи, що на контролі
//     */
//    CW_CALENDAR_CONTROL,
//    /**
//     * виконані документи
//     */
//    CW_CALENDAR_COMPLETED,
//    /**
//     * термін виконання
//     */
//    CW_CALENDAR_TERM,
//    /**
//     * документи, для яких термін виконання минув
//     */
//    CW_CALENDAR_EXPIRED,
//    /**
//     * виконано контрольних документів
//     */
//    CW_CALENDAR_COMPLETED_CONTROL,
//    /**
//     * документи на контролі виконано з порушенням
//     */
//    CW_CALENDAR_COMPLETED_CONTROL_WITH_VIOLATIONS,
//    /**
//     * документи виконано з порушенням
//     */
//    CW_CALENDAR_COMPLETED_WITH_VIOLATIONS,
//    /**
//     * документи на контролі термін минув
//     */
//    CW_CALENDAR_CONTROL_EXPIRED,
//
//    /**
//     * sql оператор exists
//     */
//    EXISTS,
//    /**
//     * sql оператор exists без порівнняня на sign = 0
//     */
//    EXISTS_NO_SIGN,
//    /**
//     * sql оператор not exists
//     */
//    NOT_EXISTS,
//    /**
//     * більше ніж або рівне виразу
//     */
//    GREATER_THAN_OR_EQUALS_EXPRESSION,
//    /**
//     * менше ніж вираз
//     */
//    LESS_THAN_EXPRESSION,
//    /**
//     * дата між датами
//     */
//    DATE_BETWEEN,
//
//    ITEM_POSITION_MARKER;
//
            default:
                throw new UnsupportedOperationException("Filter with type :'"
                        + filterDefEnum + "' not supported. Fix me");
        }
    }

}
