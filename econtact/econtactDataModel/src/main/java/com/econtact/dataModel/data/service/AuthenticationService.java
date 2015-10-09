package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;

public interface AuthenticationService {

	AdvanceUserEntity getUserByLogin(String login);
}
