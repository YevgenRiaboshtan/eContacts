package com.econtact.authWeb.app.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.econtact.dataModel.data.filter.AbstractFieldFilterDef;
import com.econtact.dataModel.data.filter.FilterDataTypeEnum;
import com.econtact.dataModel.data.filter.FilterDefEndsWithIgnoreCase;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.filter.FilterDefGreater;
import com.econtact.dataModel.data.filter.FilterDefGreaterEq;
import com.econtact.dataModel.data.filter.FilterDefIsNull;
import com.econtact.dataModel.data.filter.FilterDefLess;
import com.econtact.dataModel.data.filter.FilterDefLessEq;
import com.econtact.dataModel.data.filter.FilterDefLike;
import com.econtact.dataModel.data.filter.FilterDefNotEquals;
import com.econtact.dataModel.data.filter.FilterDefNotNull;
import com.econtact.dataModel.data.filter.FilterDefStartsWithIgnoreCase;
import com.econtact.dataModel.data.util.EntityHelper;

public final class FilterUtils {
	//Cached String value used in filtering.
	
	/**
	 * Empty String value.
	 */
	public static final String EMPTY_STRING = "".intern();
	
	/**
	 * "<>" - represents Not equals filter type.
	 */
	public static final String NOT_EQUAL_CHARACTER = "<>".intern();
	/**
	 *  "=" - represents equal filter type.
	 */
	public static final String EQUAL_CHARACTER = "=".intern();
	/**
	 *  "_" - represents containt filter type.  
	 */
	public static final String CONTAINS_CHARACTER = "_".intern();
	/**
	 * "@" - represents IS NULL filter type.
	 */
	public static final String IS_NULL_CHARACTER = "@".intern();
	/**
	 * "!@" - represents IS NOT NULL filter type.
	 */
	public static final String IS_NOT_NULL_CHARACTER = "!@".intern();
	/**
	 * "%" - represents LIKE filter type.
	 */
	public static final String LIKE_CHARACTER = "%".intern();
	/**
	 * ">" - represents great filter type.
	 */
	public static final String GREAT_CHARACTER = ">".intern();
	/**
	 * ">=" - represents great or equals filter type.
	 */
	public static final String GREAT_OR_EQUAL_CHARACTER = ">=".intern();
	/**
	 * "<" - represents less filter type.
	 */
	public static final String LESS_CHARACTER = "<".intern();
	/**
	 * "<=" - represents less or equals filter type.
	 */
	public static final String LESS_OR_EQUAL_CHARACTER = "<=".intern();
	
	public static AbstractFieldFilterDef getMakeFilter(FilterDataTypeEnum dataType, String field, Object value) {
		AbstractFieldFilterDef result = null;
		switch (dataType) {
		case TEXT:
			result = makeTextFilter(field, value);
			break;
		case LONG:
			result = makeNumberFilter(field, value, true);
			break;
		case NUMBER:
			result = makeNumberFilter(field, value, false);
			break;
		case BOOLEAN:
			result = new FilterDefEquals(field, value);
			break;
		case DATE:
			result = makeDateFilter(field, value.toString());
			break;
		case DICTIONARY:
			result = new FilterDefEquals(field, value);
			break;
		case ENUM:
			result = new FilterDefEquals(field, value);
			break;
		case SIGN:
			if (Boolean.valueOf((String) value)) {
				result = new FilterDefEquals(field, EntityHelper.ACTUAL_SIGN);
			} else {
				result = new FilterDefNotEquals(field, EntityHelper.ACTUAL_SIGN);
			}
			break;
		case NONE:
		default:
			break;
		}
		return result;
	}
	
	private static AbstractFieldFilterDef makeTextFilter(String field, Object value) {
		String searchLine = ((String) value).trim();
		if (searchLine.isEmpty()) {
			return null;
		}
		if (searchLine.startsWith(NOT_EQUAL_CHARACTER)) {
			return new FilterDefNotEquals(field, searchLine.substring(NOT_EQUAL_CHARACTER.length()).trim());
		}
		if (searchLine.startsWith(EQUAL_CHARACTER)) {
			return new FilterDefEquals(field, searchLine.substring(EQUAL_CHARACTER.length()).trim());
		}
		if (searchLine.contains(CONTAINS_CHARACTER)) {
			return new FilterDefLike(field, searchLine);
		}
		if (IS_NULL_CHARACTER.equals(searchLine)) {
			return new FilterDefIsNull(field);
		}
		if (IS_NOT_NULL_CHARACTER.equals(searchLine)) {
			return new FilterDefNotNull(field);
		}
		if (searchLine.indexOf(LIKE_CHARACTER) == 0
				&& searchLine.lastIndexOf(LIKE_CHARACTER) == 0) {
			return new FilterDefEndsWithIgnoreCase(field, searchLine.substring(LIKE_CHARACTER.length()).trim());
		}
		if (searchLine.indexOf(LIKE_CHARACTER) == searchLine.length() - 1
				&& searchLine.indexOf(LIKE_CHARACTER) == searchLine.lastIndexOf(LIKE_CHARACTER)) {
			return new FilterDefStartsWithIgnoreCase(field, searchLine.substring(0, searchLine.length() - 1).trim());
		}
		if (searchLine.indexOf(LIKE_CHARACTER) >= 0) {
			return new FilterDefLike(field, searchLine);
		}
		return new FilterDefStartsWithIgnoreCase(field, searchLine);
	}
	
	private static AbstractFieldFilterDef makeNumberFilter(String field, Object value, boolean isLong) {
		String searchLine = ((String) value).trim();
		if (searchLine.isEmpty()) {
			return null;
		}
		if (searchLine.startsWith(NOT_EQUAL_CHARACTER)) {
			return new FilterDefNotEquals(
					field,
					isLong 
						? Long.valueOf(searchLine.substring(NOT_EQUAL_CHARACTER.length()).trim())
						: BigDecimal.valueOf(Long.valueOf(searchLine.substring(NOT_EQUAL_CHARACTER.length()).trim())));
		}
		if (searchLine.startsWith(LESS_OR_EQUAL_CHARACTER)) {
			return new FilterDefLessEq(
					field,
					isLong
						? Long.valueOf(searchLine.substring(LESS_OR_EQUAL_CHARACTER.length()).trim())
						: BigDecimal.valueOf(Long.valueOf(searchLine.substring(LESS_OR_EQUAL_CHARACTER.length()).trim())));
		}
		if (searchLine.startsWith(LESS_CHARACTER)) {
			return new FilterDefLess(
					field,
					isLong 
						? Long.valueOf(searchLine.substring(LESS_CHARACTER.length()).trim())
						: BigDecimal.valueOf(Long.valueOf(searchLine.substring(LESS_CHARACTER.length()).trim())));
		}
		if (searchLine.startsWith(GREAT_OR_EQUAL_CHARACTER)){
			return new FilterDefGreaterEq(
					field,
					isLong
						? Long.valueOf(searchLine.substring(GREAT_OR_EQUAL_CHARACTER.length()).trim())
						: BigDecimal.valueOf(Long.valueOf(searchLine.substring(GREAT_OR_EQUAL_CHARACTER.length()).trim())));
		}
		if (searchLine.startsWith(GREAT_CHARACTER)) {
			return new FilterDefGreater(
					field,
					isLong
						? Long.valueOf(searchLine.substring(GREAT_CHARACTER.length()).trim())
						: BigDecimal.valueOf(Long.valueOf(searchLine.substring(GREAT_CHARACTER.length()).trim())));
		}
		if (IS_NULL_CHARACTER.equals(searchLine)) {
			return new FilterDefIsNull(field);
		}
		if (IS_NOT_NULL_CHARACTER.equals(searchLine)) {
			return new FilterDefNotNull(field);
		}
		if (searchLine.startsWith(EQUAL_CHARACTER)) {
			searchLine = searchLine.substring(EQUAL_CHARACTER.length()).trim();
		}
		return new FilterDefEquals(
					field,
					isLong 
						? Long.valueOf(searchLine)
						: BigDecimal.valueOf(Long.valueOf(searchLine)));
	}
	
	private static AbstractFieldFilterDef makeDateFilter(String field, String value) {
		try {
			if (value.startsWith(NOT_EQUAL_CHARACTER)) {
				return new FilterDefNotEquals(field, convertDate(value.substring(NOT_EQUAL_CHARACTER.length())));
			}
			if (value.startsWith(LESS_OR_EQUAL_CHARACTER)) {
				return new FilterDefLessEq(field, convertDate(value.substring(LESS_OR_EQUAL_CHARACTER.length())));
			}
			if (value.startsWith(GREAT_OR_EQUAL_CHARACTER)) {
				return new FilterDefGreaterEq(field, convertDate(value.substring(GREAT_OR_EQUAL_CHARACTER.length())));
			}
			if (value.startsWith(GREAT_CHARACTER)) {
				return new FilterDefGreater(field, convertDate(value.substring(GREAT_CHARACTER.length())));
			}
			if (value.startsWith(LESS_CHARACTER)) {
				return new FilterDefLess(field, convertDate(value.substring(LESS_CHARACTER.length())));
			}
			if (value.startsWith(EQUAL_CHARACTER)) {
				value = value.substring(EQUAL_CHARACTER.length());
			}
			return new FilterDefEquals(field, convertDate(value));
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date convertDate(String sDate) throws ParseException {
		String splitterString;
		/*
		 * Проверка даты на соответствие формату
		 * дд.мм.гггг
		 * разделителем могут быть симовлы . / -
		 * а может и не быть вовсе
		 */
		Pattern p = Pattern.compile("[\\d]{1,8}|[\\d]{1,2}\\.[\\d]{1,6}|[\\d]{1,2}\\.[\\d]{1,2}\\.[\\d]{1,4}|[\\d]{1,2}\\-[\\d]{1,6}|[\\d]{1,2}\\-[\\d]{1,2}\\-[\\d]{1,4}|[\\d]{1,2}\\/[\\d]{1,6}|[\\d]{1,2}\\/[\\d]{1,2}\\/[\\d]{1,4}".intern());
		if (!p.matcher(sDate).matches()) {
			throw new ParseException(EMPTY_STRING, 0);
		}		
		
		if (sDate.contains(".".intern())) {
			splitterString = ".";
		} else if (sDate.contains("/".intern())) {
			splitterString = "/";
		} else if (sDate.contains("-".intern())) {
			splitterString = "-";
		} else {
			splitterString = EMPTY_STRING;
		}
		
		if (StringUtils.isNotBlank(splitterString)) {
			String[] dateStrings = sDate.split("\\" + splitterString);
			switch (dateStrings.length) {
			case 3:
				return parseStringToDate(dateStrings[0], dateStrings[1], dateStrings[2]);
			case 2:
				return parseStringToDate(dateStrings[0], dateStrings[1], null);
			case 1:
				return parseStringToDate(dateStrings[0], null, null);
			default:
				throw new ParseException(EMPTY_STRING, 0);
			}
		} else {
			switch (sDate.length()) {
			case 1:
			case 2:
				return parseStringToDate(sDate, null, null);
			case 3:
				//ABC - date string;
				try{
					//AB.C
					return parseStringToDate(sDate.substring(0, 2),sDate.substring(2, 3) , null);
				} catch(ParseException e){
					
				}
				try{
					//A.BC
					return parseStringToDate(sDate.substring(0, 1),sDate.substring(1, 3) , null);
				} catch (ParseException e){
					
				}
				try{
					//A.B.C
					return parseStringToDate(sDate.substring(0, 1),sDate.substring(1, 2) , sDate.substring(2, 3));
				} catch (ParseException e){
					
				}
				throw new ParseException(EMPTY_STRING, 0);
			case 6:
				//add F
			case 5:
				//add E
			case 4:
				//ABCD(EF) - dateString 
				try{
					//AB.CD (EF)
					return parseStringToDate(sDate.substring(0, 2), sDate.substring(2,4), 5 == sDate.length() || 6 == sDate.length() ? sDate.substring(4) : null);
				} catch(ParseException e){
					
				}
				try{
					//AB.C.D (EF)
					return parseStringToDate(sDate.substring(0, 2), sDate.substring(2, 3), sDate.substring(3));
				} catch (ParseException e){
					
				}
				try{
					//A.BC.D (EF)
					return parseStringToDate(sDate.substring(0, 1), sDate.substring(1, 3), sDate.substring(3));
				} catch (ParseException e){
					
				}
				try{
					//A.B.CD (EF)
					return parseStringToDate(sDate.substring(0, 1), sDate.substring(1, 2), sDate.substring(2));
				} catch (ParseException e){
					
				}
				throw new ParseException(EMPTY_STRING, 0);
			case 7:
				//ABCDEFG - dateString 
				try{
					//AB.CD.EFG
					return parseStringToDate(sDate.substring(0, 2), sDate.substring(2,4), sDate.substring(4));
				} catch(ParseException e){
					
				}
				try{
					//AB.C.DEFG
					return parseStringToDate(sDate.substring(0, 2), sDate.substring(2, 3), sDate.substring(3));
				} catch (ParseException e){
					
				}
				try{
					//A.BC.DEFG
					return parseStringToDate(sDate.substring(0, 1), sDate.substring(1, 3), sDate.substring(3));
				} catch (ParseException e){
					
				}
				throw new ParseException(EMPTY_STRING, 0);
			case 8:
				// ABCDEFGH - date string
				//AB.CD.EFGH
				return parseStringToDate(sDate.substring(0, 2), sDate.substring(2, 4), sDate.substring(4));
			default:
				throw new ParseException(EMPTY_STRING, 0);
			}
		}
	}
	
	private static Date parseStringToDate(String days, String mount, String year) throws ParseException {
		if (days == null && mount == null && year == null) {
			throw new ParseException("", 0);
		}
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		if (year != null) {
			if (year.length() <= 4 && Integer.parseInt(year) > 0) {
				// Если вводят только 3 последние цифры года то добавлять 2 чтобы было 2...
				if (year.length() == 3) {
					year = "2".intern() + year;
				}
				// Если вводят только 2 последние цифры года то добавлять 20 чтобы было 20..
				if (year.length() == 2) {
					year = "20".intern() + year;
				}
				// Если год указан одно цифрой то добавлять 200 в начало.
				if (year.length() == 1) {
					year = "200".intern() + year;
				}
				date.set(Calendar.YEAR, Integer.parseInt(year));
			} else {
				throw new ParseException(EMPTY_STRING, 0);
			}
		}
		if (mount != null) {
			if (mount.length() <= 2 && Integer.parseInt(mount) > 0 && Integer.parseInt(mount) <= 12) {
				date.set(Calendar.MONTH, Integer.parseInt(mount) - 1);
			} else {
				throw new ParseException(EMPTY_STRING, 0);
			}
		}
		if (days != null) {
			if (days.length() <= 2 && Integer.parseInt(days) > 0
					&& Integer.parseInt(days) <= date.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(days));
			} else {
				throw new ParseException(EMPTY_STRING, 0);
			}
		}
		return date.getTime();
	}
}
