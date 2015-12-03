package com.econtact.dataModel.model.entity.accout;

import com.econtact.dataModel.model.AbstractEnum;

public enum ConfirmStatusEnum implements AbstractEnum {
	NOT_CONFIRMED,
	CONFIRMED;

	@Override
	public String getLabelKey() {
		switch (this) {
		case NOT_CONFIRMED:	
			return "confirmStatusEnum.notConfirmed.label";
		case CONFIRMED:
			return "confirmStatusEnum.confirmed.label";
		default:
			return "";
		}
	}

	@Override
	public Object getValue() {
		return this;
	}
}
