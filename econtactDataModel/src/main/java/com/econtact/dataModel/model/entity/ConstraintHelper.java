package com.econtact.dataModel.model.entity;

import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

/**
 * Class contains constraint names in database.
 * @author Yevgen Riaboshtan
 *
 */
public final class ConstraintHelper {

	//AccessChurchEntity section
	/**
	 * {@link AccessChurchEntity} name with sign unique
	 */
	public static final String  ACCESS_CHURCH_NAME_SIGN = "ACCESS_CHURCH_1";
	
	
	//AccountUserEntity section
	/**
	 * {@link AccountUserEntity} login with sign unique
	 */
	public static final String ACCOUNT_USER_LOGIN_SIGN = "ACCOUNT_USER_1";
	/**
	 * {@link AccountUserEntity#getLogin()} index by login
	 */
	public static final String ACCOUNT_USER_LOGIN_INDEX = "ACCOUNT_USER_I_1";
	
	//ChurchEntity section
	/**
	 * {@link ChurchEntity} name with sign unique
	 */
	public static final String CHURCH_NAME_SIGN = "CHURCH_1";
	
	//UniverDictEntity Section
	/**
	 * {@link UniverDictEntity} paramDict, idRecDict, Sign unique
	 */
	public static final String UNIVER_DICT_PARAM_ID_REC_DICT_SIGN ="UNIVER_DICT_1";
	
	
	
	private ConstraintHelper() {}
}
