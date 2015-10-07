package com.econtact.dataModel.data.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.QueryHints;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.context.EnversContext;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.EjbService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.AuditSupport;
import com.econtact.dataModel.model.entity.dictionary.DictionaryConstant;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public abstract class AbstractGenericService implements EjbService {

	@EJB
	UniverDictService univerDictService;

	@Override
	public <T extends AbstractView> T findById(Class<T> findClass, Object id) {
		return getEntityManager().find(findClass, id);
	}

	@Override
	public <T extends AbstractEntity> T saveOrUpdate(T entity, UserContext userContext) {
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
		// todo handle uniqueconstraintexception and other
		return getEntityManager().merge(entity);
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
		return find(criteria, null, null);
	}

	@Override
	public <T> List<T> find(SearchCriteria<T> criteria, Integer from, Integer count) {
		final Query query = criteria.getSelectQuery(getEntityManager());
		if (from != null && from > 0) {
			query.setFirstResult(from);
		}
		if (count != null && count > 0) {
			query.setMaxResults(count);
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