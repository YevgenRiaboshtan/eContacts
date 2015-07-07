package org.auth.dataModel.data.util;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public final class EntityHelper {

	public static final String AUTH_PU = "authPU";
	public static final String AUTH_SCHEMA= "authschema";
	
	public static final String ID_F = "id";
	public static final String SIGN_F = "sign";
	public static final String UPD_DATE_F = "upd_date";
	public static final String UPD_AUTHOR_F = "upd_author";
	public static final BigDecimal ACTUAL_SIGN = BigDecimal.ZERO;
	
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
