package org.auth.dataModel.model.entity.accout;

public enum RoleType {
	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN");
	
	RoleType(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName(){
		return name;
	}
}
