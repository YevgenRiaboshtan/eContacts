package com.econtact.authWeb.app.helpers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.inputtext.InputText;

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
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.model.AbstractEnum;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@ManagedBean(name = "filterHelper")
@ApplicationScoped
public class FilterHelper implements Serializable{
	private static final long serialVersionUID = 3408088178055266453L;

	@EJB
	UniverDictService univerDictService;
	
	/**
	 * Возвращает список елементов комбобокса для фильтра
	 * @param dictionaryName - название справочника
	 * @return
	 */
	public List<SelectItem> getUniverDictFilter(String dictionaryName) {
		List<SelectItem> items = new ArrayList<>();
		SelectItem allItem = new SelectItem(null, "");
		allItem.setNoSelectionOption(true);
		items.add(allItem);
		for (UniverDictEntity item : univerDictService.findUniverDictByParamDict(dictionaryName)){
			items.add(new SelectItem(item, item.getNameRecDict()));
		}
		return items;
	}
	
	/**
	 * Возвращает список елементов комбобокса для фильтра
	 * @param enumClassName - тип enum 
	 * @return список элементов фильтра
	 * @throws ClassNotFoundException 
	 */
	public List<SelectItem> getEnumFilter(String enumClassName) throws ClassNotFoundException {
		List<SelectItem> items = new ArrayList<SelectItem>();
		Class<?> clazz = Class.forName(enumClassName);
		if (!clazz.isEnum()) {
			throw new IllegalArgumentException("enumType should be enumeration !!!");
		}
		if (!AbstractEnum.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("enumType should implements DmsEnum !!!");
		}
		SelectItem allItem = new SelectItem(null,"");
		allItem.setNoSelectionOption(true);
		items.add(allItem);
		AbstractEnum[] constraints = (AbstractEnum[]) clazz.getEnumConstants();
		for (AbstractEnum constraint : constraints) {
			items.add(new SelectItem(constraint.getValue(), LabelsHelper.getLocalizedMessage(constraint.getLabelKey())));		
		}
		return items;
	}
	
	public AbstractFieldFilterDef getMakeFilter(FilterDataTypeEnum dataType, String field, Object value) {
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
		case NONE:
		default:
			break;
		}
		return result;
	}
	
	public FilterDataTypeEnum parseDataTypeString(String dataType) {
		switch (dataType) {
		case "TEXT":
			return FilterDataTypeEnum.TEXT;
		case "DATE":
			return FilterDataTypeEnum.DATE;
		case "LONG":
			return FilterDataTypeEnum.LONG;
		case "NUMBER":
			return FilterDataTypeEnum.NUMBER;
		case "ENUM":
			return FilterDataTypeEnum.ENUM;
		case "DICTIONARY":
			return FilterDataTypeEnum.DICTIONARY;
		case "BOOLEAN":
			return FilterDataTypeEnum.BOOLEAN;
		case "NONE":
			return FilterDataTypeEnum.NONE;
		default:
			return null;
		}
	}
	
	private AbstractFieldFilterDef makeNumberFilter(String field, Object value, boolean isLong) {
		String searchLine = ((String) value).trim();
		if (searchLine.isEmpty()) {
			return null;
		}
		if (searchLine.startsWith("<>")) {
			return new FilterDefNotEquals(
					field,
					isLong 
						? Long.valueOf(searchLine.substring("<>".length()).trim())
						: new BigDecimal(searchLine.substring("<>".length()).trim()));
		}
		if (searchLine.startsWith("<=")) {
			return new FilterDefLessEq(
					field,
					isLong
						? Long.valueOf(searchLine.substring("<=".length()).trim())
						: new BigDecimal(searchLine.substring("<=".length()).trim()));
		}
		if (searchLine.startsWith("<")) {
			return new FilterDefLess(
					field,
					isLong 
						? Long.valueOf(searchLine.substring("<".length()).trim())
						: new BigDecimal(searchLine.substring("<".length()).trim()));
		}
		if (searchLine.startsWith(">=")){
			return new FilterDefGreaterEq(
					field,
					isLong
						? Long.valueOf(searchLine.substring(">=".length()).trim())
						: new BigDecimal(searchLine.substring(">=".length()).trim()));
		}
		if (searchLine.startsWith(">")) {
			return new FilterDefGreater(
					field,
					isLong
						? Long.valueOf(searchLine.substring(">".length()).trim())
						: new BigDecimal(searchLine.substring(">".length()).trim()));
		}
		if ("@".equals(searchLine)) {
			return new FilterDefIsNull(field);
		}
		if ("!@".equals(searchLine)) {
			return new FilterDefNotNull(field);
		}
		if (searchLine.startsWith("=")) {
			searchLine = searchLine.substring("=".length()).trim();
		}
		return new FilterDefEquals(
					field,
					isLong 
						? Long.valueOf(searchLine)
						: new BigDecimal(searchLine));
	}

	private AbstractFieldFilterDef makeTextFilter(String field, Object value) {
		String searchLine = ((String) value).trim();
		if (searchLine.isEmpty()) {
			return null;
		}
		if (searchLine.startsWith("<>")) {
			return new FilterDefNotEquals(field, searchLine.substring("<>".length()).trim());
		}
		if (searchLine.startsWith("=")) {
			return new FilterDefEquals(field, searchLine.substring("=".length()).trim());
		}
		if (searchLine.contains("_")) {
			return new FilterDefLike(field, searchLine);
		}
		if ("@".equals(searchLine)) {
			return new FilterDefIsNull(field);
		}
		if ("!@".equals(searchLine)) {
			return new FilterDefNotNull(field);
		}
		if (searchLine.indexOf("%") == 0
				&& searchLine.lastIndexOf("%") == 0) {
			return new FilterDefEndsWithIgnoreCase(field, searchLine.substring("%".length()).trim());
		}
		if (searchLine.indexOf("%") == searchLine.length() - 1
				&& searchLine.indexOf("%") == searchLine.lastIndexOf("%")) {
			return new FilterDefStartsWithIgnoreCase(field, searchLine.substring(0, searchLine.length() - 1).trim());
		}
		if (searchLine.indexOf("%") >= 0) {
			return new FilterDefLike(field, searchLine);
		}
		return new FilterDefStartsWithIgnoreCase(field, searchLine);
	}
	
	private AbstractFieldFilterDef makeDateFilter(String field, String value) {
		try {
			if (value.startsWith("<>")) {
				return new FilterDefNotEquals(field, convertDate(value.substring("<>".length())));
			}
			if (value.startsWith("<=")) {
				return new FilterDefLessEq(field, convertDate(value.substring("<=".length())));
			}
			if (value.startsWith(">=")) {
				return new FilterDefGreaterEq(field, convertDate(value.substring(">=".length())));
			}
			if (value.startsWith(">")) {
				return new FilterDefGreater(field, convertDate(value.substring(">".length())));
			}
			if (value.startsWith("<")) {
				return new FilterDefLess(field, convertDate(value.substring("<".length())));
			}
			if (value.startsWith("=")) {
				value = value.substring("=".length());
			}
			return new FilterDefEquals(field, convertDate(value));
		} catch (ParseException e) {
			return null;
		}
	}
		
	
	public Date convertDate(String sDate) throws ParseException {
		String splitterString = "";
		/*
		 * Проверка даты на соответствие формату
		 * дд.мм.гггг
		 * разделителем могут быть симовлы . / -
		 * а может и не быть вовсе
		 */
		Pattern p = Pattern.compile("[\\d]{1,8}|[\\d]{1,2}\\.[\\d]{1,6}|[\\d]{1,2}\\.[\\d]{1,2}\\.[\\d]{1,4}|[\\d]{1,2}\\-[\\d]{1,6}|[\\d]{1,2}\\-[\\d]{1,2}\\-[\\d]{1,4}|[\\d]{1,2}\\/[\\d]{1,6}|[\\d]{1,2}\\/[\\d]{1,2}\\/[\\d]{1,4}");
		if (!p.matcher(sDate).matches()) {
			throw new ParseException("", 0);
		}		
		
		if (sDate.contains(".")) {
			splitterString = ".";
		} else if (sDate.contains("/")) {
			splitterString = "/";
		} else if (sDate.contains("-")) {
			splitterString = "-";
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
				throw new ParseException("", 0);
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
				throw new ParseException("", 0);
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
				throw new ParseException("", 0);
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
				throw new ParseException("", 0);
			case 8:
				// ABCDEFGH - date string
				//AB.CD.EFGH
				return parseStringToDate(sDate.substring(0, 2), sDate.substring(2, 4), sDate.substring(4));
			default:
				throw new ParseException("", 0);
			}
		}
	}

	private Date parseStringToDate(String days, String mount, String year) throws ParseException {
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
				// Если вводят только 3 последние цифры года то добавлять 1 чтобы было 1...
				if (year.length() == 3) {
					year = "1" + year;
				}
				// Если вводят только 2 последние цифры года то добавлять 20 чтобы было 20..
				if (year.length() == 2) {
					year = "20" + year;
				}
				// Если год указан одно цифрой то добавлять 200 в начало.
				if (year.length() == 1) {
					year = "200" + year;
				}
				date.set(Calendar.YEAR, Integer.parseInt(year));
			} else {
				throw new ParseException("", 0);
			}
		}
		if (mount != null) {
			if (mount.length() <= 2 && Integer.parseInt(mount) > 0 && Integer.parseInt(mount) <= 12) {
				date.set(Calendar.MONTH, Integer.parseInt(mount) - 1);
			} else {
				throw new ParseException("", 0);
			}
		}
		if (days != null) {
			if (days.length() <= 2 && Integer.parseInt(days) > 0
					&& Integer.parseInt(days) <= date.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(days));
			} else {
				throw new ParseException("", 0);
			}
		}
		return date.getTime();
	}
	
	public void dateAutocomplete(AjaxBehaviorEvent  event) {
		if (event != null
				&& event.getSource() instanceof InputText
				&& StringUtils.isNotBlank(((InputText) event.getSource()).getValue().toString())) {
			InputText input = (InputText) event.getSource();
			String value = input.getValue().toString();
			String operation = "";
			if (value.startsWith("<>")
				|| value.startsWith("<=")
				|| value.startsWith(">=")
				|| value.startsWith("!@")) {
				operation = value.substring(0, "<>".length());
				value = value.substring("<>".length());
			} else if (value.startsWith("<")
					|| value.startsWith(">")
					|| value.startsWith("=")
					|| value.startsWith("@")) {
				operation = value.substring(0, "<".length());
				value = value.substring("<".length());
			}
			String date;
			try {
				date = new SimpleDateFormat(WebHelper.DATE_PATTERN).format(convertDate(value));
				input.setValue(operation + date);
			} catch (ParseException e) {
				input.setValue("");
			}
		}
	}
	
}
