package com.econtact.dataModel.model.entity.accout;

public enum ActiveStatusEnum {
	DISABLE,
	ENABLE;
	
	public ActiveStatusEnum getValue() {
		return this;
	}
	
	public String getLabelKey() {
			return this.toString();
	}
}
