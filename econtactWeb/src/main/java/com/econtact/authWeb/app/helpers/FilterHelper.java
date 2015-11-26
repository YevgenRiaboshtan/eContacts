package com.econtact.authWeb.app.helpers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.FilterEvent;

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
	
	
	public String convertDate(String sDate) {
		String result = "";
		String splitterString = "";
		if (sDate.contains(".")) {
			if (sDate.contains("-")
				|| sDate.contains("/")) {
				return result;
			}
			splitterString = ".";
		} else if(sDate.contains("-")) {
			if (sDate.contains("/")) {
				return result;
			}
			splitterString = "-";
		} else if (sDate.contains("/")) {
			splitterString = "/";
		}
		
		//length check
		if (sDate.length() == 0
			|| (sDate.contains(splitterString) && sDate.length() > 10)
			|| (!sDate.contains(splitterString) && sDate.length() > 8)) {
			return result;
		}
		
		//regex check;
		
		if (sDate.contains(splitterString)) {
			String[] dateStrings = sDate.split("\\" + splitterString);
			switch (dateStrings.length) {		
			case 3:
				
				break;
			case 2:
				break;
			case 1:
				
				
				break;
			default:
				result = "";
				break;
			}
		}
		return result;
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
			String date = convertDate(value);
			input.setValue(date.isEmpty() ? "" : operation + date);
		}
	}
	
}
