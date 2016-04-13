package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public interface AuthenticationService {

	AccountUserEntity getUserByLogin(String login);
	
	void connectUser(SessionUserEntity user, String sessionId, String ipAddress, String deviceName);
	
	void disconnectUser(String sessionId);
}
