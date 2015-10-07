package com.econtact.dataModel.data.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.EntityHelper;

@Stateless
@Local(GenericService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GenericServiceImpl extends AbstractGenericService implements GenericService{

	@PersistenceContext(unitName=EntityHelper.E_CONTACT_PU)
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
