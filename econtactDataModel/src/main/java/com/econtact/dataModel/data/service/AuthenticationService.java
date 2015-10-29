package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public interface AuthenticationService {

	AccountUserEntity getUserByLogin(String login);
	
	void connectUser(String deviceName, String ipAddr, SessionUserEntity user);
	
	void disconnectUser(String ipAddr, SessionUserEntity user);
}
