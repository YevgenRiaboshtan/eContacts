package com.econtact.authWeb.app.view.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.econtact.dataModel.model.entity.accout.RoleType;

@FacesConverter(value = "roleTypeConverter")
public class RoleTypeConverter extends EnumConverter {

	public RoleTypeConverter() {
		super(RoleType.class);
	}
}
