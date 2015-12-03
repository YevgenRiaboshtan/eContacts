package com.econtact.authWeb.app.view.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;

@FacesConverter(value = "confirmStatusEnumConverter")
public class ConfirmStatusEnumConverter extends EnumConverter {

	public ConfirmStatusEnumConverter() {
		super(ConfirmStatusEnum.class);
	}
}
