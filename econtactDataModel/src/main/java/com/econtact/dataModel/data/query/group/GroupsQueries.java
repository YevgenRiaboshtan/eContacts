package com.econtact.dataModel.data.query.group;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.econtact.dataModel.data.filter.visitor.VisitorContext;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.access.AccessGroupEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

public class GroupsQueries extends GenericFilterDefQueries<GroupEntity> {
	private static final long serialVersionUID = 7707002692676680580L;

	private final SessionUserEntity user;

	public GroupsQueries(SessionUserEntity user) {
		super(GroupEntity.class);
		this.user = user;
	}
	
	protected Predicate getAdditionalPredicate(VisitorContext visitorCtx, CriteriaQuery cq) {
		final CriteriaBuilder cb = visitorCtx.getCriteriaBuilder();
		
		final Subquery<GroupEntity> accessSubq = cq.subquery(GroupEntity.class);
		final Root<AccessGroupEntity> accessRoot = accessSubq.from(AccessGroupEntity.class);
		accessSubq.select(accessRoot.get(AccessGroupEntity.GROUP_A));
		accessSubq.where(cb.and(
				cb.equal(accessRoot.get(EntityHelper.SIGN_A), visitorCtx.createFindParam(EntityHelper.ACTUAL_SIGN)),
				cb.equal(accessRoot.get(AccessGroupEntity.CONFIRM_A), visitorCtx.createFindParam(true)),
				cb.equal(accessRoot.get(AccessGroupEntity.USER_A), visitorCtx.createFindParam(user)),
				cb.or(
					cb.equal(accessRoot.get(AccessGroupEntity.VIEW_PERMIT_A), visitorCtx.createFindParam(true)),
					cb.equal(accessRoot.get(AccessGroupEntity.EDIT_PERMIT_A), visitorCtx.createFindParam(true)))));
		return cb.and(
				cb.equal(visitorCtx.getRoot().get(EntityHelper.SIGN_A), visitorCtx.createFindParam(EntityHelper.ACTUAL_SIGN)),
				cb.in(visitorCtx.getRoot()).value(accessSubq));
		
	}
}
