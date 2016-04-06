package com.econtact.authWeb.app.utils;

import java.util.HashMap;
import java.util.Map;

import com.econtact.authWeb.app.constraint.ContraintViewRelation;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.ConstraintHelper;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public class UniqueConstraintHandleUtils {

	private static UniqueConstraintHandleUtils INSTANCE = new UniqueConstraintHandleUtils();

	private Map<Class, Map<String, ContraintViewRelation>> constraints;

	public static UniqueConstraintHandleUtils getInstance() {
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

	private void loadConstraints() {
		constraints = new HashMap<Class, Map<String, ContraintViewRelation>>();
		Map<String, ContraintViewRelation> advancedUser = new HashMap<String, ContraintViewRelation>();
		advancedUser.put(ConstraintHelper.ACCOUNT_USER_LOGIN_SIGN, new ContraintViewRelation(ConstraintHelper.ACCOUNT_USER_LOGIN_SIGN, "newUser.user.login.already.exist.error.message"));
		constraints.put(AccountUserEntity.class, advancedUser);
		Map<String, ContraintViewRelation> church = new HashMap<String, ContraintViewRelation>();
		church.put(ConstraintHelper.CHURCH_NAME_SIGN, new ContraintViewRelation(ConstraintHelper.CHURCH_NAME_SIGN, "church.name.sign.unique.constraint"));
		constraints.put(ChurchEntity.class, church);
	}

	private UniqueConstraintHandleUtils() {
		loadConstraints();
	}
}
