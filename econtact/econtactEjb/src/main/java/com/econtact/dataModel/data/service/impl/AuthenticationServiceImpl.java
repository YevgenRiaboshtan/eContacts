package com.econtact.dataModel.data.service.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.accout.UserEntity;

@Stateless
@Local(AuthenticationService.class)
public class AuthenticationServiceImpl implements AuthenticationService {

	@EJB 
	private UniverDictService univerDictService;
	
	@PersistenceContext(unitName = EntityHelper.E_CONTACT_PU)
	private EntityManager em;

	@Override
	public UserEntity getUserByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity getUserByPhone(Long phone) {
		// TODO Auto-generated method stub
		return null;
	}
}
