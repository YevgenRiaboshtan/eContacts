package com.econtact.dataModel.data.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.econtact.dataModel.data.filter.FilterDefEqualsIgnoreCase;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

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
		criteria.andFilter(new FilterDefEqualsIgnoreCase(AccountUserEntity.LOGIN_A, login));
		List<AccountUserEntity> result = criteria.getSelectQuery(em).getResultList();	
		return result.isEmpty() ? null : result.get(0);
	}
}
