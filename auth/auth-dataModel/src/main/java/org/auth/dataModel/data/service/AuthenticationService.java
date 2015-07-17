package org.auth.dataModel.data.service;

import org.auth.dataModel.model.entity.accout.UserEntity;

public interface AuthenticationService {

	UserEntity getUserByNameOrEmail(String login);
}
