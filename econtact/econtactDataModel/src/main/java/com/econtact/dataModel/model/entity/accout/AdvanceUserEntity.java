package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA, 
	uniqueConstraints = { 
		@UniqueConstraint(name = AdvanceUserEntity.USER_LOGIN_SIGN_UNIQUE_CONSTRAINT,
							columnNames = {AbstractUserEntity.LOGIN_A, EntityHelper.SIGN_F })})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class AdvanceUserEntity extends AbstractUserEntity {
	private static final long serialVersionUID = 5694870185179400515L;
	public static final String USER_LOGIN_SIGN_UNIQUE_CONSTRAINT = "user_login_sign_unique_constraint";
	public static final String DISABLE_ACCOUNT_A = "disabledAccount";

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "salt", nullable = false, length = 40)
	private String salt;

	@Column(name = "enabled_account", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ActiveStatusEnum enabledAccount = ActiveStatusEnum.DISABLE;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = UserRoleRel.USER_A, orphanRemoval = true, cascade = { CascadeType.ALL })
	@Where(clause = "sign = 0")
	@Fetch(FetchMode.SELECT)
	private Set<UserRoleRel> roles = new HashSet<>();

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUpdAuthor() {
		return updAuthor;
	}

	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public BigDecimal getSign() {
		return sign;
	}

	public void setSign(BigDecimal sign) {
		this.sign = sign;
	}

	public ActiveStatusEnum getEnabledAccount() {
		return enabledAccount;
	}
	
	public Boolean isEnablesAccount() {
		return ActiveStatusEnum.ENABLE.equals(enabledAccount);
	}

	public void setEnabledAccount(ActiveStatusEnum enabledAccoutn) {
		this.enabledAccount = enabledAccoutn;
	}

	public void addRole(RoleType role, AccessStatusEnum confirm) {
		for (UserRoleRel item : roles) {
			if (role.equals(item.getRole())) {
				return;
			}
		}
		UserRoleRel newRole = new UserRoleRel();
		newRole.setConfirm(confirm);
		newRole.setRole(role);
		newRole.setUser(this);
		roles.add(newRole);
	}

	public void removeRole(RoleType role) {
		Iterator<UserRoleRel> iterator = roles.iterator();
		while (iterator.hasNext()) {
			UserRoleRel item = iterator.next();
			if (role.equals(item.getRole())) {
				iterator.remove();
				break;
			}
		}
	}

	public Set<UserRoleRel> getRoles() {
		return roles;
	}

	@PrePersist
	public void prePersist() {
		sign = EntityHelper.ACTUAL_SIGN;
		updAuthor = EJBContext.get().getUser().getUpdData();
		updDate = new Date();
	}

}
