package com.econtact.dataModel.data.context;

import java.io.Serializable;
import java.util.TimeZone;

import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

/**
 * User context class contain information of the current user that modify entities.
 * 
 * Контекст пользователя производящего изменения. Применяется в аудировании и записи информации и пользователе, который
 * производил изменния.
 * 
 * @author Yevgen Riaboshtan
 *
 */
public class UserContext implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link SessionUserEntity} object of the current user.
	 * 
	 * {@link SessionUserEntity} объект текущего пользователя.
	 */
	private SessionUserEntity user;
	/**
	 * Time zone of the current user.
	 * 
	 * Временная зона текущего пользователя.
	 */
	private TimeZone userTimeZone;

	/**
	 * Method create user context instance for user {@link SessionUserEntity} and userTimeZone {@link TimeZone}.
	 * 
	 * Метод создает новый контекст пользователя на основе объекта пользователя {@link SessionUserEntity} и временной
	 * зоны {@link TimeZone} .
	 * 
	 * @param user
	 *            - user {@link SessionUserEntity}. 
	 *            - Пользователь {@link SessionUserEntity} для котрого будет созда контекст.
	 * 
	 * @param userTimeZone
	 *            - user time zone {@link TimeZone}. 
	 *            - Временная зона пользователя {@link TimeZone}.
	 * 
	 * @return - new user context {@link UserContext} instance. 
	 * 			- созданный контекст пользователя {@link UserContext}.
	 */
	public static UserContext create(SessionUserEntity user, TimeZone userTimeZone) {
		final UserContext result = new UserContext();
		result.user = user;
		result.userTimeZone = userTimeZone;
		return result;
	}

	/**
	 * Return timezone of the context.
	 * 
	 * Возвращает временну зону контекста.
	 * 
	 * @return - contexts timezone. 
	 * 			- временная зона контекста.
	 */
	public TimeZone getUserTimeZone() {
		return userTimeZone;
	}

	/**
	 * Return current {@link SessionUserEntity} entity of the current user.
	 * 
	 * Возвращает объект текщуего пользователя контекста {@link SessionUserEntity}.
	 * 
	 * @return - {@link SessionUserEntity} of the current user. 
	 * 			- {@link SessionUserEntity} текущего пользователя.
	 */
	public SessionUserEntity getUser() {
		return user;
	}
}
