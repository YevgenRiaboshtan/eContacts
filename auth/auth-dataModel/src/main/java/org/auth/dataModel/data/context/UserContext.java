package org.auth.dataModel.data.context;

import java.io.Serializable;
import java.util.TimeZone;

import org.auth.dataModel.model.entity.accout.UserEntity;

public class UserContext implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UserEntity user;
	private TimeZone userTimeZone;
	
	public static UserContext create(UserEntity user, TimeZone userTimeZone){
		final UserContext result = new UserContext();
		result.user = user;
		result.userTimeZone = userTimeZone;
		return result;
	}

	public TimeZone getUserTimeZone() {
		return userTimeZone;
	}

	public UserEntity getUser() {
		return user;
	}
}
