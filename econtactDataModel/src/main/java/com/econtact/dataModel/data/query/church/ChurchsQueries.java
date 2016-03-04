package com.econtact.dataModel.data.query.church;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.econtact.dataModel.data.filter.visitor.VisitorContext;
import com.econtact.dataModel.data.query.GenericFilterDefQueries;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public class ChurchsQueries extends GenericFilterDefQueries<ChurchEntity> {
	private static final long serialVersionUID = 5016430460425168813L;
	
	private final SessionUserEntity user;
	
	public ChurchsQueries(SessionUserEntity user) {
		super(ChurchEntity.class);
		this.user = user;
	}
	
	protected Predicate getAdditionalPredicate(VisitorContext visitorCtx, CriteriaQuery cq) {
		final CriteriaBuilder cb = visitorCtx.getCriteriaBuilder();
		
		Subquery<BigDecimal> accessSubq = cq.subquery(BigDecimal.class);
		Root<AccessChurchEntity> accessRoot = accessSubq.from(AccessChurchEntity.class);
		accessSubq.select(accessRoot.get(AccessChurchEntity.CHURCH_A).get(EntityHelper.ID_A));
		accessSubq.where(cb.and(
				cb.equal(accessRoot.get(EntityHelper.SIGN_A), visitorCtx.createFindParam(EntityHelper.ACTUAL_SIGN)),
				cb.equal(accessRoot.get(AccessChurchEntity.CONFIRM_A), visitorCtx.createFindParam(true)),
				cb.or(
					cb.equal(accessRoot.get(AccessChurchEntity.VIEW_PERMIT_A), visitorCtx.createFindParam(true)),
					cb.equal(accessRoot.get(AccessChurchEntity.EDIT_PERMIT_A), visitorCtx.createFindParam(true))),
				cb.equal(accessRoot.get(AccessChurchEntity.USER_A), visitorCtx.createFindParam(user))));

		return cb.and(
				cb.in(visitorCtx.getRoot().get(EntityHelper.ID_A)).value(accessSubq),
				cb.equal(visitorCtx.getRoot().get(EntityHelper.SIGN_A), visitorCtx.createFindParam(EntityHelper.ACTUAL_SIGN)));
	}
}
