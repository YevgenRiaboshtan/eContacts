package com.econtact.dataModel.data.util;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

public final class EntityHelper {

	public static final String E_CONTACT_PU = "econtactPU";
	public static final String E_CONTACT_SCHEMA= "econtactschema";

	public static final String POINT = ".";
	public static final String ID_A = "id";
	public static final String ID_F = "id";
	public static final String SIGN_F = "sign";
	public static final String SIGN_A = "sign";
	public static final String UPD_DATE_F = "upd_date";
	public static final String UPD_AUTHOR_F = "upd_author";
	public static final BigDecimal ACTUAL_SIGN = BigDecimal.ZERO;
	
	/*--- event audit constants FIXME localization ----*/
    public static final String EV_NAME_CREATE = "Создание";
    public static final String EV_NAME_UPDATE = "Редактирование";
    public static final String EV_NAME_REMOVE = "Удаление";
	
	private static AtomicLong identifyGenerator = new AtomicLong();

	
	/**
	 * Unique Constans codes
	 */
	
	/**
	 * {@link AccessChurchEntity} name with sign unique
	 */
	public static final String  ACCESS_CHURCH_NAME_SIGN = "ACCESS_CHURCH_1";
	
	/**
	 * {@link AccountUserEntity} login with sign unique
	 */
	public static final String ACCOUNT_USER_LOGIN_SIGN = "ACCOUNT_USER_1";
	
	/**
	 * {@link AccountUserEntity#getLogin()} index by login
	 */
	public static final String ACCOUNT_USER_LOGIN_INDEX = "ACCOUNT_USER_I_1";
	
	/**
	 * {@link ChurchEntity} name with sign unique
	 */
	public static final String CHURCH_NAME_SIGN = "CHURCH_1";
	
	
	/**
	 * {@link UniverDictEntity} paramDict, idRecDict, Sign unique
	 */
	public static final String UNIVER_DICT_PARAM_ID_REC_DICT_SIGN ="UNIVER_DICT_1";
	
	/**
	 * Don't create instance of helper class.
	 */
	private EntityHelper() {
	}

	public static Long getUid() {
		return identifyGenerator.incrementAndGet();
	}
}
