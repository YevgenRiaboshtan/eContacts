package com.econtact.authWeb.app.view.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@FacesConverter(value="univerDictConvert")
public class UniverDictConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		return WebUtils.getBean(UniverDictService.class).findById(new BigDecimal(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		return ((UniverDictEntity) value).getId().toString();
	}

}
