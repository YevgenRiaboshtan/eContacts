package com.econtact.dataModel.data.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.QueryHints;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.context.EnversContext;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.EjbService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.AuditSupport;
import com.econtact.dataModel.model.entity.dictionary.DictionaryConstant;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractGenericService implements EjbService {

	@EJB
	UniverDictService univerDictService;

	@Override
	public <T extends AbstractView> T findById(Class<T> findClass, Object id) {
		return findById(findClass, id, null);
	}
	
	@Override
	public <T extends AbstractView> T findById(Class<T> findClass, Object id, String grapthName) {
		Map<String, Object> hints = new HashMap();
		if (StringUtils.isNotBlank(grapthName)) {
			hints.put(QueryHints.LOADGRAPH, getEntityManager().getEntityGraph(grapthName));
		}
		return getEntityManager().find(findClass, id, hints);
	}
	
	@Override
	public <T extends AbstractEntity> T saveOrUpdate(T entity, UserContext userContext) throws UniqueConstraintException {
		EJBContext.get().setUserContext(userContext);
		if (entity instanceof AuditSupport) {
			final UniverDictEntity event = univerDictService.findByParamDictAndIdRecDict(NamesDictConstant.EVENT,
					entity.getId() == null ? DictionaryConstant.EVENT_CREATE : DictionaryConstant.EVENT_UPDATE);
			final String evName = entity.getId() == null ? EntityHelper.EV_NAME_CREATE : EntityHelper.EV_NAME_UPDATE;
			final String note = ((AuditSupport) entity).getEnversNote();
			EJBContext.get().setEnversContext(EnversContext.create(event, evName, note));
		} else {
			EJBContext.get().setEnversContext(null);
		}
		T result = null;
		try{
			result = getEntityManager().merge(entity);
			getEntityManager().flush();
			return result;
		} catch (OptimisticLockException ex) {
			throw ex; 
		} catch (PersistenceException ex) {
			if (ex.getCause().getCause() instanceof SQLException
					&& ((SQLException) ex.getCause().getCause()).getSQLState().equalsIgnoreCase(UniqueConstraintException.POSTGRESQL_UNIQUE_CONSTRAINT_EXCEPTION_CODE)) {
				throw new UniqueConstraintException(entity.getClass(), (SQLException) ex.getCause().getCause());
			}
		}
		return result;
	}

	@Override
	public void remove(AbstractEntity entity, UserContext userContext) {
		EJBContext.get().setUserContext(userContext);
		if (entity instanceof AuditSupport) {
			final UniverDictEntity event = univerDictService.findByParamDictAndIdRecDict(NamesDictConstant.EVENT,
					DictionaryConstant.EVENT_REMOVE);
			final String note = ((AuditSupport) entity).getEnversNote();
			EJBContext.get().setEnversContext(EnversContext.create(event, EntityHelper.EV_NAME_REMOVE, note));
		} else {
			EJBContext.get().setEnversContext(null);
		}
		final AbstractEntity toRemove = getEntityManager().find(entity.getClass(), entity.getId());
		getEntityManager().remove(toRemove);
	}

	@Override
	public <T> T findSingleResult(SearchCriteria<T> criteria) {
		TypedQuery<T> query = criteria.getSelectQuery(getEntityManager());
		final T result = query.getSingleResult();
		return result;
	}

	@Override
	public <T> List<T> find(SearchCriteria<T> criteria) {
		return find(criteria, null, null, null);
	}
	
	@Override
	public <T> List<T> find(SearchCriteria<T> criteria, String graphName) {
		return find(criteria, null, null, graphName);
	}

	@Override
	public <T> List<T> find(SearchCriteria<T> criteria, Integer from, Integer count) {
		return find(criteria, from, count, null);
	}

	@Override
	public <T> List<T> find(SearchCriteria<T> criteria, Integer from, Integer count, String graphName) {
		final Query query = criteria.getSelectQuery(getEntityManager());
		if (from != null && from > 0) {
			query.setFirstResult(from);
		}
		if (count != null && count > 0) {
			query.setMaxResults(count);
		}
		if (StringUtils.isNotBlank(graphName)) {
			query.setHint(QueryHints.LOADGRAPH, getEntityManager().getEntityGraph(graphName));
		}
		final List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public <T> Long getRowCount(SearchCriteria<T> criteria) {
		final TypedQuery<Long> query = criteria.getRowCountQuery(getEntityManager());
		query.setHint(QueryHints.READ_ONLY, Boolean.TRUE);
		final Long result = query.getSingleResult();
		return result;
	}

	protected abstract EntityManager getEntityManager();
}
