package com.econtact.authWeb.app.beans.helper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;


public final class CacheUtils {
		
	//FIXME change to the sofr reference? maybe change to id only for cached entities
	private final static Map<String, BigDecimal> churchCache = new HashMap<>();
	private final static Map<String, BigDecimal> userCache = new HashMap<>();
	
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
	
	private CacheUtils() {}
}
