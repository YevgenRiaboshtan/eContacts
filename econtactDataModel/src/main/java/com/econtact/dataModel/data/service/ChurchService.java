package com.econtact.dataModel.data.service;

import java.util.Collection;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public interface ChurchService {

	ChurchEntity saveOrUpdate(ChurchEntity church, UserContext userContext, Collection<AccessChurchEntity> modify, Collection<AccessChurchEntity> toRemove) throws UniqueConstraintException;
}
