package com.econtact.dataModel.data.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.filter.FilterDefEqualsIgnoreCase;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.common.ConnectAuditEntity;

@Stateless
@Local(AuthenticationService.class)
public class AuthenticationServiceImpl implements AuthenticationService {

	@EJB
	private UniverDictService univerDictService;

	@PersistenceContext(unitName = EntityHelper.E_CONTACT_PU)
	private EntityManager em;

	@Override
	public AccountUserEntity getUserByLogin(String login) {
		SearchCriteria<AccountUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AccountUserEntity.class));
		criteria.andFilter(new FilterDefEqualsIgnoreCase(AccountUserEntity.LOGIN_A, login))
				.andFilter(new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN));
		List<AccountUserEntity> result = criteria.getSelectQuery(em).getResultList();	
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public void connectUser(SessionUserEntity user, String sessionId, String ipAddress, String deviceName) {
        final ConnectAuditEntity logEntry = ConnectAuditEntity.create(user, sessionId, ipAddress, deviceName);
        em.persist(logEntry);
    }

	@Override
	public void disconnectUser(String sessionId) {
       em.createQuery("Update ConnectAuditEntity entity set entity.endVisit=:date where entity.sessionId=:sessionId")
       		.setParameter("date", Calendar.getInstance().getTime())
       		.setParameter("sessionId", sessionId)
       		.executeUpdate();
    }
}
