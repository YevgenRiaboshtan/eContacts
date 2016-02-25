package com.econtact.dataModel.data.service;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public interface ChurchService {

	ChurchEntity saveOrUpdate(ChurchEntity church, UserContext userContext) throws UniqueConstraintException;
}
