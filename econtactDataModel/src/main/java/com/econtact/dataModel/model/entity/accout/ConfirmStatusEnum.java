package com.econtact.dataModel.model.entity.accout;

import com.econtact.dataModel.model.AbstractEnum;

public enum ConfirmStatusEnum implements AbstractEnum {
	NOT_CONFIRMED,
	CONFIRMED;

	@Override
	public String getLabelKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue() {
		return this;
	}
}
