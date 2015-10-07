package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;

public interface AuthenticationService {

	AdvanceUserEntity findUser(String login);
	
	AdvanceUserEntity getUserByLogin(String login);
	
	AdvanceUserEntity getUserByEmail(String email);
}
