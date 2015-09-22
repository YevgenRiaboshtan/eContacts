package com.econtact.dataModel.model.entity.accout;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.econtact.dataModel.data.util.EntityHelper;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserEntity extends AbstractUserEntity {
	private static final long serialVersionUID = -3747397876193741856L;

}
