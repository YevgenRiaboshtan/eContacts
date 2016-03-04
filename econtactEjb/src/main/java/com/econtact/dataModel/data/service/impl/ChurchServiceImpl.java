package com.econtact.dataModel.data.service.impl;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.ChurchService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

@Stateless
@Local(ChurchService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChurchServiceImpl extends GenericServiceImpl implements ChurchService {

	@Override
	public ChurchEntity saveOrUpdate(ChurchEntity church, UserContext userContext,
			Collection<AccessChurchEntity> modify, Collection<AccessChurchEntity> toRemove,
			Collection<GroupEntity> groups, Collection<GroupEntity> groupsToRemove) throws UniqueConstraintException {
		final ChurchEntity result = super.saveOrUpdate(church, userContext);
		if (church.getId() == null && RoleType.ROLE_ADMIN.equals(userContext.getUser().getRole())) {
			AccessChurchEntity access = new AccessChurchEntity();
			access.setChurch(result);
			access.setUser(userContext.getUser());
			access.setConfirm(true);
			access.setAddContactPermit(true);
			access.setEditContactPermit(true);
			access.setEditGroupPermit(true);
			access.setEditPermit(true);
			access.setEditUserPermit(true);
			access.setViewPermit(true);
			access.setEditAccessPermit(true);
			getEntityManager().merge(access);
		}
		toRemove.forEach(item -> super.remove(item, userContext));
		modify.forEach(item -> {
			try {
				item.setChurch(result);
				super.saveOrUpdate(item, userContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		groupsToRemove.forEach(item -> super.remove(item, userContext));
		groups.forEach(item -> {
			try {
				item.setChurch(result);
				super.saveOrUpdate(item, userContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return church;
	}

}
