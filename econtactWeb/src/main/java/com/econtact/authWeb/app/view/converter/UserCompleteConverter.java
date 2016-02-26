package com.econtact.authWeb.app.view.converter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@FacesConverter(value = "userAutocompleteConverter")
public class UserCompleteConverter implements Converter {

	private String USERS_ATTR = "autocompleteUsers";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return WebUtils.getBean(GenericService.class).findById(SessionUserEntity.class, ((Map)component.getAttributes().get(USERS_ATTR)).get(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Map<String, BigDecimal> cache = (Map<String, BigDecimal>) component.getAttributes().get(USERS_ATTR);
		if (cache == null) {
			cache = new HashMap<String, BigDecimal>();
			component.getAttributes().put(USERS_ATTR, cache);
		}
		((Map) component.getAttributes().get(USERS_ATTR)).put(((SessionUserEntity) value).getLogin(), ((SessionUserEntity)value).getId());
		return ((SessionUserEntity) value).getLogin();
	}
}
