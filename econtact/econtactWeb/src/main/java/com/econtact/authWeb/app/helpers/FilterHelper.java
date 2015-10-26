package com.econtact.authWeb.app.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import com.econtact.dataModel.data.filter.AbstractFieldFilterDef;
import com.econtact.dataModel.data.filter.FilterDefEnum;
import com.econtact.dataModel.data.filter.FilterDefLike;
import com.econtact.dataModel.model.AbstractEnum;

@ManagedBean
@ApplicationScoped
public class FilterHelper implements Serializable{
	private static final long serialVersionUID = 3408088178055266453L;

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
	
	
	
	public String getLikeFilterType() {
		return FilterDefEnum.LIKE.toString();
	}
	
	public AbstractFieldFilterDef getFilterType(String type, String field, Object value) {
		switch (type) {
		case "LIKE":
			return new FilterDefLike(field, value.toString());
			default:
				throw new IllegalArgumentException("Unknown filterType");
		}
	}
}
