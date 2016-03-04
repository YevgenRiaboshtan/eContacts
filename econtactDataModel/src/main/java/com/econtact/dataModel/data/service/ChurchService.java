package com.econtact.dataModel.data.service;

import java.util.Collection;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

public interface ChurchService {

	ChurchEntity saveOrUpdate(ChurchEntity church, UserContext userContext, 
			Collection<AccessChurchEntity> modify, Collection<AccessChurchEntity> toRemove,
			Collection<GroupEntity> groups, Collection<GroupEntity> groupsToRemove) throws UniqueConstraintException;
}
