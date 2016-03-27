package com.econtact.dataModel.model.entity.accout;

import com.econtact.dataModel.model.AbstractEnum;

public enum RoleType implements AbstractEnum {
	/** Супер Админ:	
	 * 		создает администраторов (владельцев проекта)*/
	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"),
	/** 
	 * Администрато:
	 * 		- создает проекты
	 * 		- создает сотрудников подчиненные ему
	 */
	ROLE_ADMIN("ROLE_ADMIN"),
	/**
	 * Сотрудник:
	 * 		- работает над проектом
	 * 		- если дан доступ администратором то может создавать проекты и создавать регистраторов для работы в них 	
	 */
	ROLE_EMPLOYEE("ROLE_EMPLOYEE"),
	/**
	 * Регистратор:
	 * 		- заполняет новые контакты
	 */
	ROLE_REGISTER("ROLE_REGISTER");
	
	private String name;
	
	RoleType(String name) {
		this.name = name;
	}	
	
	public String getName(){
		return name;
	}

	@Override
	public String getLabelKey() {
		switch (this) {
		case ROLE_SUPER_ADMIN:
			return "roleType.super.admin";
		case ROLE_ADMIN:
			return "roleType.admin";
		case ROLE_EMPLOYEE:
			return "roleType.employee";
		case ROLE_REGISTER:
			return "roleType.register";
		default:
			return "";
		}
	}

	@Override
	public Object getValue() {
		return this;
	}
}
