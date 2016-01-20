package com.econtact.authWeb.app.beans.helper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.inputtext.InputText;

import com.econtact.authWeb.app.utils.FilterUtils;
import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.AbstractEnum;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@Named
@ApplicationScoped
public class FilterHelper implements Serializable{
	private static final long serialVersionUID = 3408088178055266453L;

	@EJB
	UniverDictService univerDictService;
	
	@Inject
	LabelsHelper labelsHelper;
	
	/**
	 * Возвращает список елементов комбобокса для фильтра
	 * @param dictionaryName - название справочника
	 * @return
	 */
	public List<SelectItem> getUniverDictSelectItems(String dictionaryName) {
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
	 * @param optional - добавлять ли пусто элемент комбобокса
	 * @return список элементов фильтра
	 * @throws ClassNotFoundException 
	 */
	public List<SelectItem> getEnumSelectItems(String enumClassName, boolean optional) throws ClassNotFoundException {
		List<SelectItem> items = new ArrayList<SelectItem>();
		Class<?> clazz = Class.forName(enumClassName);
		if (!clazz.isEnum()) {
			throw new IllegalArgumentException("enumType should be enumeration !!!");
		}
		if (!AbstractEnum.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("enumType should implements AbstractEnum !!!");
		}
		if (optional) {
			SelectItem allItem = new SelectItem(null,"");
			allItem.setNoSelectionOption(true);
			items.add(allItem);
		}
		AbstractEnum[] constraints = (AbstractEnum[]) clazz.getEnumConstants();
		for (AbstractEnum constraint : constraints) {
			items.add(new SelectItem(constraint.getValue(), labelsHelper.getLocalizedMessage(constraint.getLabelKey())));		
		}
		return items;
	}
	
	/**
	 * Build complete date
	 * @param event blur event
	 */
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
				date = new SimpleDateFormat(WebUtils.DATE_PATTERN).format(FilterUtils.convertDate(value));
				input.setValue(operation + date);
			} catch (ParseException e) {
				input.setValue("");
			}
		}
	}
	
	
	public List<SelectItem> getSignSelectItems(boolean optional) {
		List<SelectItem> result = new ArrayList<>();
		if (optional) {
			SelectItem allItem = new SelectItem(null,"");
			allItem.setNoSelectionOption(true);
			result.add(allItem);
		}
		result.add(new SelectItem(true, labelsHelper.getLocalizedMessage(LocaleLabels.SIGN_FILTER_ACTUAL)));
		result.add(new SelectItem(false, labelsHelper.getLocalizedMessage(LocaleLabels.SIGN_FILTER_DELETE)));
		
		return result;
	}
}
