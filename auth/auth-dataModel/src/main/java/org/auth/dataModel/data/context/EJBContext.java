package org.auth.dataModel.data.context;

import java.util.TimeZone;

import org.auth.dataModel.model.entity.accout.UserEntity;

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

	public UserEntity getUser() {
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
