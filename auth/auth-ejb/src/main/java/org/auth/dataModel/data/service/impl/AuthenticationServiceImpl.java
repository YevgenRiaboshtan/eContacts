package org.auth.dataModel.data.service.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.auth.dataModel.data.service.AuthenticationService;
import org.auth.dataModel.data.service.UniverDictService;
import org.auth.dataModel.data.util.EntityHelper;
import org.auth.dataModel.model.entity.accout.UserEntity;

@Stateless
@Local(AuthenticationService.class)
public class AuthenticationServiceImpl implements AuthenticationService {

	@EJB 
	private UniverDictService univerDictService;
	
	@PersistenceContext(unitName = EntityHelper.AUTH_PU)
	private EntityManager em;
	
	@Override
	public UserEntity getUserByNameOrEmail(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
