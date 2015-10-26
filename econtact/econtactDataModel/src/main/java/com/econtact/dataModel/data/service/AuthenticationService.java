package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

public interface AuthenticationService {

	AccountUserEntity getUserByLogin(String login);
}
