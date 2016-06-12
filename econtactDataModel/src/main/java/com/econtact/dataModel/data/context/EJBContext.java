package com.econtact.dataModel.data.context;

import java.util.TimeZone;

import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
/**
 * 
 * EJB Context have user context {@link UserContext} and envers context {@link EnversContext}.
 * 
 * EJB Контекст включет контекст пользователя {@link UserContext} и конетекст аудирования операции {@link EnversContext}.
 * 
 * @author Yevgen Riaboshtan
 *
 */
public class EJBContext {
	
	/**
	 * User context {@link UserContext}.
	 * 
	 * Контекст пользователя.
	 */
	private UserContext userContext;
	
	/**
	 * Envers context {@link EnversContext}.
	 * 
	 * Контекст аудирования операции.
	 */
	private EnversContext enversContext;

	/**
	 * Locale Instance of the EJBContext.
	 * 
	 * Локальное состояние EJB контекста.
	 */
	private static ThreadLocal<EJBContext> instance = new ThreadLocal<EJBContext>() {

		@Override
		protected EJBContext initialValue() {
			return new EJBContext();
		}
	};

	/**
	 * Private default constructor.
	 * 
	 * Внутренний конструктор по умолчанию.
	 */
	private EJBContext() {
	}

	/**
	 * Return local EJBContext {@link EJBContext} instance.
	 * 
	 * Возвращает локальное состояние EJB контекста {@link EJBContext}.
	 * 
	 * @return - local EJBContext {@link EJBContext} instance.
	 * 		- локальное состояние EJB контекста {@link EJBContext}.
	 */
	public static EJBContext get() {
		return instance.get();
	}

	/**
	 * Set user context {@link UserContext}.
	 * 
	 * 
	 * 
	 * @param userContext - new user context {@link UserContext}.
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * Return user from user context. 
	 * @return - user from user context.
	 */
	public SessionUserEntity getUser() {
		return userContext.getUser();
	}

	/**
	 * Return user`s context time zone.
	 * @return - user`s context time zone.
	 */
	public TimeZone getUserTimeZone() {
		return userContext.getUserTimeZone();
	}

	/**
	 * Return envers context.
	 * @return - envers context.
	 */
	public EnversContext getEnversContext() {
		return enversContext;
	}

	/**
	 * Set envers context.
	 * @param enversContext - new envers context.
	 */
	public void setEnversContext(EnversContext enversContext) {
		this.enversContext = enversContext;
	}
}
