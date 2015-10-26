package com.econtact.dataModel.data.context;

import java.io.Serializable;
import java.util.TimeZone;

import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public class UserContext implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private SessionUserEntity user;
	private TimeZone userTimeZone;
	
	public static UserContext create(SessionUserEntity user, TimeZone userTimeZone){
		final UserContext result = new UserContext();
		result.user = user;
		result.userTimeZone = userTimeZone;
		return result;
	}

	public TimeZone getUserTimeZone() {
		return userTimeZone;
	}

	public SessionUserEntity getUser() {
		return user;
	}
}
