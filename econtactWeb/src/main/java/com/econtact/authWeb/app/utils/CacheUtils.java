package com.econtact.authWeb.app.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;


public final class CacheUtils {
		
	//FIXME change to the sofr reference? maybe change to id only for cached entities
	private static volatile Map<String, BigDecimal> churchCache = new HashMap<>();
	private static volatile Map<String, BigDecimal> userCache = new HashMap<>();
	
	public static void putChurch(ChurchEntity church) {
		if (!churchCache.containsKey(church.getNameChurch())) {
			churchCache.put(church.getNameChurch(), church.getId());
		}
	}
	
	public static ChurchEntity getChurch(String churchName) {
		BigDecimal id = churchCache.get(churchName);
		if (id == null) {
			return null;
		}
		return WebUtils.getBean(GenericService.class).findById(ChurchEntity.class, id);
	}
	
	public static void putUser(SessionUserEntity user) {
		if (!userCache.containsKey(user.getLogin())) {
			userCache.put(user.getLogin(), user.getId());
		}
	}
	
	public static SessionUserEntity getUser(String login) {
		BigDecimal id = userCache.get(login);
		if (id == null) {
			return null;
		}
		return WebUtils.getBean(GenericService.class).findById(SessionUserEntity.class, id);
	}

	
	//FIXME костыль костыльный
	// Надо научить хибер кешировать эти списки 
	private static volatile Map<BigDecimal, Boolean> clearGroupAccessCache = new ConcurrentHashMap<BigDecimal, Boolean>();
	private static volatile Map<BigDecimal, Boolean> clearChurchAccessCache = new ConcurrentHashMap<BigDecimal, Boolean>();
	
	public static synchronized boolean needClearGroupAccess(BigDecimal userId) {
		if (!clearGroupAccessCache.containsKey(userId)) {
			clearGroupAccessCache.put(userId, Boolean.FALSE);
		}
		Boolean result = clearGroupAccessCache.get(userId);
		clearGroupAccessCache.put(userId, Boolean.FALSE);
		return result;
	}
	
	public static synchronized void clearGroupAccessCache() {
		for (Entry<BigDecimal, Boolean> entry : clearGroupAccessCache.entrySet() ) {
			entry.setValue(Boolean.TRUE);
		}
	}
	
	public static synchronized boolean needClearChurchAccess(BigDecimal userId) {
		if (!clearChurchAccessCache.containsKey(userId)) {
			clearChurchAccessCache.put(userId, Boolean.FALSE);
		}
		Boolean result = clearChurchAccessCache.get(userId);
		clearChurchAccessCache.put(userId, Boolean.FALSE);
		return result;
	}
	
	public static synchronized void clearChurchAccessCache() {
		for (Entry<BigDecimal, Boolean> entry : clearChurchAccessCache.entrySet()) {
			entry.setValue(Boolean.TRUE);
		}
	}
	
 	private CacheUtils() {}
}
