package com.econtact.authWeb.app.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.econtact.authWeb.app.utils.CacheUtils;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@FacesConverter(value = "userAutocompleteConverter")
public class UserCompleteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return CacheUtils.getUser(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		CacheUtils.putUser((SessionUserEntity) value);
		return ((SessionUserEntity) value).getLogin();
	}
}
