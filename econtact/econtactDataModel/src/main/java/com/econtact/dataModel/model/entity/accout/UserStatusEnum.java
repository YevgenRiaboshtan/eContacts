package com.econtact.dataModel.model.entity.accout;

import com.econtact.dataModel.model.AbstractEnum;

public enum UserStatusEnum implements AbstractEnum {
	DISABLE,
	ENABLE;

	@Override
	public String getLabelKey() {
		switch (this) {
		case DISABLE:	
			return "userStatusEnum.disable.label";
		case ENABLE:
			return "userStatusEnum.enable.label";
		default:
			return "";
		}
	}

	@Override
	public Object getValue() {
		return this;
	}
}
