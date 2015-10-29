package com.econtact.authWeb.app.view.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

@FacesConverter(value = "activeConverter")
public class ActiveStatusConverter extends EnumConverter {
	
	public ActiveStatusConverter() {
		super(UserStatusEnum.class);
	}
}
