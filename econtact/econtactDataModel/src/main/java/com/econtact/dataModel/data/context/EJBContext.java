package com.econtact.dataModel.data.context;

import java.util.TimeZone;

import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public class EJBContext {
	private UserContext userContext;
	private EnversContext enversContext;

	private static ThreadLocal<EJBContext> instance = new ThreadLocal<EJBContext>() {

		@Override
		protected EJBContext initialValue() {
			return new EJBContext();
		}
	};

	private EJBContext() {
	}

	public static EJBContext get() {
		return instance.get();
	}

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public SessionUserEntity getUser() {
		return userContext.getUser();
	}

	public TimeZone getUserTimeZone() {
		return userContext.getUserTimeZone();
	}

	public EnversContext getEnversContext() {
		return enversContext;
	}

	public void setEnversContext(EnversContext enversContext) {
		this.enversContext = enversContext;
	}
}
