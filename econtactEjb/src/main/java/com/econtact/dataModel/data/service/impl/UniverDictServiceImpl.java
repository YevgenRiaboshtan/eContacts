package com.econtact.dataModel.data.service.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.context.EnversContext;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.dictionary.DictionaryConstant;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictCheckEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Local(UniverDictService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UniverDictServiceImpl implements UniverDictService {

	@PersistenceContext(unitName=EntityHelper.E_CONTACT_PU)
	private EntityManager em;
	
	private final Lock lock = new ReentrantLock();
	
	private Reference<Map<String, Map<Integer, BigDecimal>>> univerDictLinksRef;
	
	@PostConstruct
	public void postConstruct() {
		final Map<String, Map<Integer, BigDecimal>> univerDictLinks = getUniverDictLinks();
		univerDictLinksRef = new SoftReference<>(univerDictLinks);
	}
	
	@Override
	public UniverDictEntity findById(BigDecimal id) {
		lock.lock();
		try {
			if (id == null) {
				return null;
			} else {
				return em.find(UniverDictEntity.class, id);
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public List<UniverDictEntity> findUniverDictByParamDict(String paramDict) {
		lock.lock();
		try {
			final Map<String, Map<Integer, BigDecimal>> univerDictLinks = getUniverDictLinks();
			final Map<Integer, BigDecimal> idRecLinks = univerDictLinks.get(paramDict);
			final List<UniverDictEntity> result = new ArrayList<>();
			if (idRecLinks == null
					|| idRecLinks.isEmpty()) {
				return result;
			}
			for (BigDecimal id : idRecLinks.values()) {
				result.add(em.find(UniverDictEntity.class, id));
			}
			return result;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public UniverDictEntity findByParamDictAndIdRecDict(String paramDict, Integer idRecDict) {
		lock.lock();
		try {
			final BigDecimal id = getUniverDictLinks().get(paramDict).get(idRecDict);
			final UniverDictEntity result = em.find(UniverDictEntity.class, id);
			return result;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public UniverDictEntity saveOrUpdate(UniverDictEntity entity,
			UserContext userContext) {
		lock.lock();
		try {
			EJBContext.get().setUserContext(userContext);
			final UniverDictEntity event = findByParamDictAndIdRecDict(NamesDictConstant.EVENT, 
					entity.getId() == null ? DictionaryConstant.EVENT_CREATE : DictionaryConstant.EVENT_UPDATE);
			final String evName = entity.getId() == null ? EntityHelper.EV_NAME_CREATE : EntityHelper.EV_NAME_UPDATE; 
			final String note = entity.getEnversNote();
			EJBContext.get().setEnversContext(EnversContext.create(event, evName, note));
			final UniverDictEntity result = em.merge(entity);
			return result;
		} finally {
			if (entity.getId() == null) {
				univerDictLinksRef = null;
			}
			lock.unlock();
		}
	}

	@Override
	public void remove(UniverDictEntity entity, UserContext userContext) {
		lock.lock();
		try {
			EJBContext.get().setUserContext(userContext);
			final UniverDictEntity event = findByParamDictAndIdRecDict(NamesDictConstant.EVENT, DictionaryConstant.EVENT_REMOVE);
			final String note = entity.getEnversNote();
			EJBContext.get().setEnversContext(EnversContext.create(event, EntityHelper.EV_NAME_REMOVE, note));
			UniverDictEntity toRemove = findById(entity.getId());
			em.remove(toRemove);
		} finally {
			univerDictLinksRef = null;
			lock.unlock();
		}
	}

	 private Map<String, Map<Integer, BigDecimal>> getUniverDictLinks() {
	        Map<String, Map<Integer, BigDecimal>> result = univerDictLinksRef == null ? null : univerDictLinksRef.get();
	        if (result == null) {
	            //Logger.info(UniverDictServiceImpl.class, "Caching UniverDictEntity by paramDict and idRecDict");
	            result = new HashMap<>();
	            univerDictLinksRef = new SoftReference<>(result);
	            final Query query = em.createNamedQuery(UniverDictEntity.FIND_ALL, UniverDictEntity.class);
	            query.setParameter(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN);
	            final List<UniverDictEntity> entities = query.getResultList();
	            for (UniverDictEntity entity : entities) {
	                Map<Integer, BigDecimal> idRecLinks = result.get(entity.getParamDict());
	                if (idRecLinks == null) {
	                    idRecLinks = new HashMap<>();
	                    result.put(entity.getParamDict(), idRecLinks);
	                }
	                idRecLinks.put(entity.getIdRecDict(), entity.getId());
	            }
	        }
	        return result;
	    }

	@Override
	public UniverDictCheckEntity saveOrUpdate(UniverDictCheckEntity entity) {
		lock.lock();
		try {
			final UniverDictCheckEntity result = em.merge(entity);
			return result;
		} finally {
			if (entity.getId() == null) {
				univerDictLinksRef = null;
			}
			lock.unlock();
		}
	}
}
