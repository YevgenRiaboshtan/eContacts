package com.econtact.dataModel.data.util;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public final class EntityHelper {

	public static final String E_CONTACT_PU = "econtactPU";
	public static final String E_CONTACT_SCHEMA= "econtactschema";
	
	public static final String ID_F = "id";
	public static final String SIGN_F = "sign";
	public static final String SIGN_A = "sign";
	public static final String UPD_DATE_F = "upd_date";
	public static final String UPD_AUTHOR_F = "upd_author";
	public static final BigDecimal ACTUAL_SIGN = BigDecimal.ZERO;
	
	/*--- event audit constants FIXME localization ----*/
    public static final String EV_NAME_CREATE = "Створення картки";
    public static final String EV_NAME_UPDATE = "Редагування картки";
    public static final String EV_NAME_REMOVE = "Знищення картки";
	
	private static AtomicLong identifyGenerator = new AtomicLong();

	/**
	 * Don't create instance of helper class.
	 */
	private EntityHelper() {
	}

	public static Long getUid() {
		return identifyGenerator.incrementAndGet();
	}
}
