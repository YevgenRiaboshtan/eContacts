package com.econtact.dataModel.data.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.econtact.dataModel.data.filter.FilterDefEquals;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;

@Stateless
@Local(AuthenticationService.class)
public class AuthenticationServiceImpl implements AuthenticationService {

	@EJB
	private UniverDictService univerDictService;

	@PersistenceContext(unitName = EntityHelper.E_CONTACT_PU)
	private EntityManager em;

	@Override
	public AdvanceUserEntity getUserByLogin(String login) {
		SearchCriteria<AdvanceUserEntity> criteria = new SearchCriteria<>(new GenericFilterDefQueries<>(AdvanceUserEntity.class));
		criteria.andFilter(new FilterDefEquals(AdvanceUserEntity.LOGIN_A, login));
		List<AdvanceUserEntity> result = criteria.getSelectQuery(em).getResultList();	
		return result.isEmpty() ? null : result.get(0);
	}
}
