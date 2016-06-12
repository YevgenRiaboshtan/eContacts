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

import com.econtact.authWeb.app.beans.view.UserSessionBean;
import com.econtact.authWeb.app.utils.FilterUtils;
import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.AbstractEnum;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

/**
 * Filter helper application bean.
 * Generate combobox elements to enum type or {@link UniverDictEntity} type filter.
 * @author Yevgen Riaboshtan
 *
 */
@Named
@ApplicationScoped
public class FilterHelper implements Serializable{
	private static final long serialVersionUID = 3408088178055266453L;

	/**
	 * UniverDict Service instance {@link UniverDictService}.
	 */
	@EJB
	UniverDictService univerDictService;
	
	/**
	 * User session bean instance {@link UserSessionBean}.
	 */
	@Inject
	UserSessionBean userSessionBean;
	
	/**
	 * Labels helper bean instance {@link LabelsHelper}.
	 */
	@Inject
	LabelsHelper labelsHelper;
	
	/**
	 * Construct {@link SelectItem} items to {@link UniverDictEntity} 
	 * @param dictionaryName - paramDict {@link UniverDictEntity#getParamDict()}  value of {@link UniverDictEntity}
	 * @param optional - empty element without value will be added if true.
	 * @return - list with {@link SelectItem} elements.
	 */
	public List<SelectItem> getUniverDictSelectItems(String dictionaryName, boolean optional) {
		List<SelectItem> items = new ArrayList<>();
		if (optional) {
			SelectItem allItem = new SelectItem(null, FilterUtils.EMPTY_STRING);
			allItem.setNoSelectionOption(true);
			items.add(allItem);
		}
		univerDictService.findUniverDictByParamDict(dictionaryName, userSessionBean.getCurrentChurch())
			.forEach(item -> items.add(new SelectItem(item, item.getNameRecDict())));
		return items;
	}
	
	/**
	 * Construct {@link SelectItem} items to {@link AbstractEnum} enum
	 * @param enumClassName - class name of the target {@link AbstractEnum} enum.
	 * @param optional - empty element without value will be added if true.
	 * @return - list with {@link SelectItem} elements.
	 * @throws ClassNotFoundException  - if wrong class name.
	 */
	public List<SelectItem> getEnumSelectItems(String enumClassName, boolean optional) throws ClassNotFoundException {
		List<SelectItem> items = new ArrayList<SelectItem>();
		Class<?> clazz = Class.forName(enumClassName);
		if (!clazz.isEnum()) {
			throw new IllegalArgumentException("enumType should be enumeration !!!".intern());
		}
		if (!AbstractEnum.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("enumType should implements AbstractEnum !!!".intern());
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
	 * Method complete date
	 * @param event blur event
	 */
	public void dateAutocomplete(AjaxBehaviorEvent  event) {
		if (event != null
				&& event.getSource() instanceof InputText
				&& StringUtils.isNotBlank(((InputText) event.getSource()).getValue().toString())) {
			InputText input = (InputText) event.getSource();
			String value = input.getValue().toString();
			String operation = "";
			if (value.startsWith(FilterUtils.NOT_EQUAL_CHARACTER)
				|| value.startsWith(FilterUtils.LESS_OR_EQUAL_CHARACTER)
				|| value.startsWith(FilterUtils.GREAT_OR_EQUAL_CHARACTER)
				|| value.startsWith(FilterUtils.IS_NOT_NULL_CHARACTER)) {
				//2 - length of the <>, <=, =>, !@
				operation = value.substring(0, 2);
				//2 - length of the <>, <=, =>, !@
				value = value.substring(2);
			} else if (value.startsWith(FilterUtils.LESS_CHARACTER)
					|| value.startsWith(FilterUtils.GREAT_CHARACTER)
					|| value.startsWith(FilterUtils.EQUAL_CHARACTER)
					|| value.startsWith(FilterUtils.IS_NULL_CHARACTER)) {
				// 1 - length of the  <, >, =, @
				operation = value.substring(0, 1);
				value = value.substring(1);
			}
			String date;
			try {
				date = new SimpleDateFormat(WebUtils.DATE_PATTERN).format(FilterUtils.convertDate(value));
				input.setValue(operation + date);
			} catch (ParseException e) {
				input.setValue(FilterUtils.EMPTY_STRING);
			}
		}
	}
	
	/**
	 * Contruct {@link SelectItem} elements to sign field.
	 * @param optional - empty element wthout value will be added if true.
	 * @return - list with {@link SelectItem} elements.
	 */
	public List<SelectItem> getSignSelectItems(boolean optional) {
		List<SelectItem> result = new ArrayList<>();
		if (optional) {
			SelectItem allItem = new SelectItem(null, FilterUtils.EMPTY_STRING);
			allItem.setNoSelectionOption(true);
			result.add(allItem);
		}
		result.add(new SelectItem(true, labelsHelper.getLocalizedMessage(LocaleLabels.SIGN_FILTER_ACTUAL)));
		result.add(new SelectItem(false, labelsHelper.getLocalizedMessage(LocaleLabels.SIGN_FILTER_DELETE)));
		
		return result;
	}
}
