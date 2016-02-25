package com.econtact.dataModel.data.query.group;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.econtact.dataModel.data.filter.visitor.VisitorContext;
import com.econtact.dataModel.data.query.AbstractFilterDefQueries;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.dto.church.GroupDto;
import com.econtact.dataModel.model.entity.access.AccessGroupEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

public class GroupsQueries extends AbstractFilterDefQueries<GroupDto> {
	private static final long serialVersionUID = 7707002692676680580L;

	private final SessionUserEntity user;
	
	public GroupsQueries(SessionUserEntity user) {
		super();
		this.user = user;
	}

	protected void prepareSelectQuery(final VisitorContext visitorCtx) {
		Join group = visitorCtx.getRoot().join(AccessGroupEntity.GROUP_A);
		group.join(GroupEntity.CHURCH_A);
		visitorCtx.getFromMap().put(AccessGroupEntity.GROUP_A, group);
	}
	
	protected void updateSelectQuery(final VisitorContext visitorCtx, final CriteriaQuery cq) {
		cq.select(visitorCtx.getCriteriaBuilder().construct(GroupDto.class, 
				visitorCtx.getRoot().get(AccessGroupEntity.GROUP_A).get(EntityHelper.ID_A),
				visitorCtx.getRoot().get(AccessGroupEntity.GROUP_A).get(GroupEntity.NAME_A),
				visitorCtx.getRoot().get(AccessGroupEntity.GROUP_A).get(GroupEntity.DESCRIPTION_A),
				visitorCtx.getRoot().get(AccessGroupEntity.VIEW_PERMIT_A),
				visitorCtx.getRoot().get(AccessGroupEntity.EDIT_PERMIT_A),
				visitorCtx.getRoot().get(AccessGroupEntity.GROUP_A).get(GroupEntity.CHURCH_A).get(ChurchEntity.NAME_CHURCH_A)));
	}
	
	protected Predicate getAdditionalPredicate(VisitorContext visitorCtx, CriteriaQuery cq) {
		final CriteriaBuilder cb = visitorCtx.getCriteriaBuilder();
		final Root root = visitorCtx.getRoot();
		return cb.and(
				cb.equal(root.get(EntityHelper.SIGN_A), visitorCtx.createFindParam(EntityHelper.ACTUAL_SIGN)),
				cb.equal(root.get(AccessGroupEntity.USER_A), visitorCtx.createFindParam(user)),
				cb.equal(root.get(AccessGroupEntity.CONFIRM_A), visitorCtx.createFindParam(ConfirmStatusEnum.CONFIRMED)));
	}

	@Override
	protected Class getViewClass() {
		return AccessGroupEntity.class;
	}
}
