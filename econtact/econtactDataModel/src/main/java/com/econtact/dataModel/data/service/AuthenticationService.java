package com.econtact.dataModel.data.service;

import com.econtact.dataModel.model.entity.accout.UserEntity;

public interface AuthenticationService {

	UserEntity getUserByNameOrEmail(String login);
}
