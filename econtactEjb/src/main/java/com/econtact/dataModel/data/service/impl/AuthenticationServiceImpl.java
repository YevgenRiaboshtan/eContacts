package com.econtact.dataModel.data.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import com.econtact.dataModel.data.filter.FilterDefAnd;
import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.filter.FilterDefEqualsIgnoreCase;
import com.econtact.dataModel.data.filter.FilterDefOr;
import com.econtact.dataModel.data.filter.visitor.VisitorContext;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.access.AccessGroupEntity;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;
import com.econtact.dataModel.model.entity.common.ConnectAuditEntity;

@Stateless
@Local(AuthenticationService.class)
public class AuthenticationServiceImpl implements AuthenticationService {

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
       		.setParameter("date", new Date())
       		.setParameter("sessionId", sessionId)
       		.executeUpdate();
    }

	@Override
	public List<ChurchEntity> getAvailableChurchs(SessionUserEntity user) {
		SearchCriteria<ChurchEntity> criteria = new SearchCriteria(new GenericFilterDefQueries(ChurchEntity.class) {
			protected void updateSelectQuery(final VisitorContext visitorCtx, final CriteriaQuery cq) {
				cq.distinct(true);
			}
		});
		criteria.andFilter(new FilterDefAnd(
				new FilterDefEquals(EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN),
				new FilterDefOr(
						new FilterDefEquals(ChurchEntity.OWNER_A, user),
						new FilterDefAnd(
								new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + AccessChurchEntity.USER_A, user),
								new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + AccessChurchEntity.CONFIRM_A, true),
								new FilterDefEquals(ChurchEntity.ACCESS_A + EntityHelper.POINT + EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN)),
						new FilterDefAnd(
								new FilterDefEquals(ChurchEntity.GROUPS_A + EntityHelper.POINT + GroupEntity.ACCESSES_A + EntityHelper.POINT + AccessGroupEntity.USER_A, user),
								new FilterDefEquals(ChurchEntity.GROUPS_A + EntityHelper.POINT + GroupEntity.ACCESSES_A + EntityHelper.POINT + AccessGroupEntity.CONFIRM_A, true),
								new FilterDefEquals(ChurchEntity.GROUPS_A + EntityHelper.POINT + GroupEntity.ACCESSES_A + EntityHelper.POINT + EntityHelper.SIGN_A, EntityHelper.ACTUAL_SIGN)))));
		return criteria.getSelectQuery(em).getResultList();
	}
}
