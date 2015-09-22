package com.econtact.dataModel.model.entity.accout;

public enum RoleType {
	ROLE_OWNER("ROLE_OWNER"),
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_MANAGER("ROLE_MANAGER"),
	ROLE_REGISTER("ROLE_REGISTER"),
	ROLE_DEF_ADMIN("ROLE_DEF_ADMIN");

	
	RoleType(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName(){
		return name;
	}
}
