package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.UserEntity;

public interface AuthenticationService {

	UserEntity getUserByLogin(String login);
	
	UserEntity getUserByEmail(String email);
	
	UserEntity getUserByPhone(Long phone);
}
