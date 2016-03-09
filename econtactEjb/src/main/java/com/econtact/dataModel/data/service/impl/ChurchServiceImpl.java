package com.econtact.dataModel.data.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.econtact.dataModel.data.service.ChurchService;

@Stateless
@Local(ChurchService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChurchServiceImpl extends GenericServiceImpl implements ChurchService {


}
