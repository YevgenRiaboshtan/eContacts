package com.econtact.authWeb.app.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.econtact.authWeb.app.beans.helper.CacheUtils;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@FacesConverter(value="churchConverter")
public class ChurchConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return CacheUtils.getChurch(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		CacheUtils.putChurch((ChurchEntity) value);
		return ((ChurchEntity) value).getNameChurch();
	}

}
