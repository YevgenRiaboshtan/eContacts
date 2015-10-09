package com.econtact.authWeb.app.utils;

import java.util.HashMap;
import java.util.Map;

import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;

public class UniqueConstraintHandleUtils {

	private static UniqueConstraintHandleUtils INSTANCE;

	private Map<Class, Map<String, ContraintViewRelation>> constraints;

	public static UniqueConstraintHandleUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UniqueConstraintHandleUtils();
			INSTANCE.loadConstraints();
		}
		return INSTANCE;
	}

	/**
	 * Return idField linked to uniqueConstraintException write in exception message.
	 * 
	 * @param exception
	 *            - unique constraint exception with exception message.
	 * @return $link{ContraintViewRelation} if constraint relation exist or null.
	 */
	public ContraintViewRelation handleException(UniqueConstraintException exception) {
		if (constraints.containsKey(exception.getEntityClass())) {
			for (Map.Entry<String, ContraintViewRelation> entry : constraints.get(exception.getEntityClass())
					.entrySet()) {
				if (exception.getMessage().contains(entry.getKey())) {
					return entry.getValue();
				}
			}
		}
		return null;
	}

	// TODO load from xml?
	private void loadConstraints() {
		constraints = new HashMap<Class, Map<String, ContraintViewRelation>>();
		Map<String, ContraintViewRelation> advancedUser = new HashMap<String, ContraintViewRelation>();
		advancedUser.put(AdvanceUserEntity.USER_LOGIN_SIGN_UNIQUE_CONSTRAINT, new ContraintViewRelation(AdvanceUserEntity.USER_LOGIN_SIGN_UNIQUE_CONSTRAINT, "userLogin", "user.login.already.exist"));
		constraints.put(AdvanceUserEntity.class, advancedUser);
	}

	private UniqueConstraintHandleUtils() {
	}
}
