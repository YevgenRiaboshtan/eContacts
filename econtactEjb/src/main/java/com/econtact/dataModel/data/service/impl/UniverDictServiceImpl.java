package com.econtact.dataModel.data.service.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Local(UniverDictService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UniverDictServiceImpl implements UniverDictService {

	@PersistenceContext(unitName = EntityHelper.E_CONTACT_PU)
	private EntityManager em;

	private final Lock lock = new ReentrantLock();

	private Reference<Map<BigDecimal, Map<String, Map<Integer, BigDecimal>>>> univerDictLinksRef;

	@PostConstruct
	public void postConstruct() {
		final Map<BigDecimal, Map<String, Map<Integer, BigDecimal>>> univerDictLinks = getUniverDictLinks();
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
	public List<UniverDictEntity> findUniverDictByParamDict(String paramDict, ChurchEntity church) {
		lock.lock();
		try {
			final Map<BigDecimal, Map<String, Map<Integer, BigDecimal>>> univerDictLinks = getUniverDictLinks();
			final Map<String, Map<Integer, BigDecimal>> churchUniverDictLinks = univerDictLinks.get(church.getId());
			if (churchUniverDictLinks == null) {
				return Collections.<UniverDictEntity>emptyList();
			}
			final Map<Integer, BigDecimal> idRecLinks = churchUniverDictLinks.get(paramDict);
			final List<UniverDictEntity> result = new ArrayList<>();
			if (idRecLinks == null || idRecLinks.isEmpty()) {
				return result;
			}
			idRecLinks.values().forEach(id -> {
				UniverDictEntity entity = em.find(UniverDictEntity.class, id);
				if (church == null
						|| church.getId().equals(entity.getChurch().getId())) {
					result.add(entity);
				}
			});
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public List<UniverDictEntity> findUniverDictByParamDict(String paramDict) {
		lock.lock();
		try {
			final Map<BigDecimal, Map<String, Map<Integer, BigDecimal>>> univerDictLinks = getUniverDictLinks();
			final List<UniverDictEntity> result = new ArrayList<UniverDictEntity>();
			for (Map<String, Map<Integer, BigDecimal>> churchUd : univerDictLinks.values()) {
				if (churchUd.containsKey(paramDict)) {
					churchUd.get(paramDict).values().forEach(id -> {
						result.add(em.find(UniverDictEntity.class, id));
					});
				}
			}
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public UniverDictEntity findByParamDictAndIdRecDict(String paramDict, Integer idRecDict, ChurchEntity church) {
		lock.lock();
		try {
			return findUniverDictByParamDict(paramDict, church).stream().filter(entity -> idRecDict.compareTo(entity.getIdRecDict()) == 0).findFirst().orElse(null);
		} finally {
			lock.unlock();
		}
	}
	
	/*@Override
	public UniverDictEntity findByParamDictAndIdRecDict(String paramDict, Integer idRecDict) {
		return findByParamDictAndIdRecDict(paramDict, idRecDict, null);
	}*/

	@Override
	public UniverDictEntity saveOrUpdate(UniverDictEntity entity, UserContext userContext) {
		lock.lock();
		try {
			EJBContext.get().setUserContext(userContext);
			final String evName = entity.getId() == null ? AbstractGenericService.EV_NAME_CREATE
					: AbstractGenericService.EV_NAME_UPDATE;
			final String note = entity.getEnversNote();
			EJBContext.get().setEnversContext(EnversContext.create(evName, note));
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
			final String note = entity.getEnversNote();
			EJBContext.get().setEnversContext(EnversContext.create(AbstractGenericService.EV_NAME_REMOVE, note));
			UniverDictEntity toRemove = findById(entity.getId());
			em.remove(toRemove);
		} finally {
			univerDictLinksRef = null;
			lock.unlock();
		}
	}

	private Map<BigDecimal, Map<String, Map<Integer, BigDecimal>>> getUniverDictLinks() {
		Map<BigDecimal, Map<String, Map<Integer, BigDecimal>>> result = univerDictLinksRef == null ? null : univerDictLinksRef.get();
		if (result == null) {
			result = new HashMap<>();
			univerDictLinksRef = new SoftReference<>(result);
			final Query query = em.createNamedQuery(UniverDictEntity.FIND_ALL, UniverDictEntity.class);
			query.setParameter(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN);
			final List<UniverDictEntity> entities = query.getResultList();
			for (UniverDictEntity entity : entities) {
				Map<String, Map<Integer, BigDecimal>> churchUdLinks = result.get(entity.getChurch().getId());
			
				if (churchUdLinks == null) {
					churchUdLinks = new HashMap<String, Map<Integer,BigDecimal>>();
					result.put(entity.getChurch().getId(), churchUdLinks);
				}
				Map<Integer, BigDecimal> idRecLinks = churchUdLinks.get(entity.getParamDict());
				if (idRecLinks == null) {
					idRecLinks = new HashMap<>();
					churchUdLinks.put(entity.getParamDict(), idRecLinks);
				}
				idRecLinks.put(entity.getIdRecDict(), entity.getId());
			}
		}
		return result;
	}
}
