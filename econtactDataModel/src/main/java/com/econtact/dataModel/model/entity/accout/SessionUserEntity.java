package com.econtact.dataModel.model.entity.accout;

import javax.persistence.Entity;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.econtact.dataModel.data.util.EntityHelper;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SessionUserEntity extends AbstractUserEntity {
	private static final long serialVersionUID = -6560661281186664579L;

	@PreUpdate
	@PreRemove
	public void PreRemove() {
		throw new UnsupportedOperationException("Can`t update/remove session users.");
	}
	
	public String getUpdData() {
		return new StringBuilder(getId().toString()).append(" - ").append(getLogin()).toString();
	}
}
