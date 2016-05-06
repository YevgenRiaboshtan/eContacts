package com.econtact.dataModel.data.service;

import java.util.List;

import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

/**
 * Authanticate service interface.
 * Define authenticate operations.
 * @author Yevgen Riaboshtan
 *
 */
public interface AuthenticationService {
	/**
	 * Find user by login in DB.
	 * @param login - login
	 * @return - {@link AccountUserEntity} user.
	 */
	AccountUserEntity getUserByLogin(String login);

	/**
	 * Get list of available churchs ({@link ChurchEntity}) for work in they.
	 * @param user - {@link SessionUserEntity} user
	 * @return - List of the available churchs.
	 */
	List<ChurchEntity> getAvailableChurchs(SessionUserEntity user);
	
	/**
	 * Write to the connect log, that user is loginned.
	 * @param user - {@link SessionUserEntity} user.
	 * @param sessionId - user session id.
	 * @param ipAddress - user ip.
	 * @param deviceName - user browser name.
	 */
	void connectUser(SessionUserEntity user, String sessionId, String ipAddress, String deviceName);
	
	/**
	 * Write time when user is logouted.
	 * @param sessionId - user session id.
	 */
	void disconnectUser(String sessionId);
}
