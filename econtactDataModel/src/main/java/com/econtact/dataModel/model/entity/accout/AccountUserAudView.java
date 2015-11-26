package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.common.AuditRevEntity;

@Entity
@Table(name = AccountUserAudView.TABLE_NAME, schema = EntityHelper.E_CONTACT_SCHEMA)
public class AccountUserAudView implements AbstractView<BigDecimal> {
	private static final long serialVersionUID = 8274738048424021382L;
	public static final String TABLE_NAME = "user_account_aud";

	@Id
	@Column(name = EntityHelper.ID_F)
	private BigDecimal id;

	@ManyToOne
	@JoinColumn(name = "rev")
	private AuditRevEntity rev;

	@Column(name = "login", nullable = false, length = 100)
	private String login;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "salt", nullable = false, length = 40)
	private String salt;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private RoleType role;

	@Enumerated
	@Column(name = "role_confirm", nullable = false)
	private ConfirmStatusEnum roleConfirm;

	@Enumerated
	@Column(name = "enabledUser", nullable = false, precision = 1, scale = 0)
	private UserStatusEnum enabledUser;

	@Column(name = "allow_create_register", nullable = false)
	private boolean allowCreateRegister;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public AuditRevEntity getRev() {
		return rev;
	}

	public void setRev(AuditRevEntity rev) {
		this.rev = rev;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public ConfirmStatusEnum getRoleConfirm() {
		return roleConfirm;
	}

	public void setRoleConfirm(ConfirmStatusEnum roleConfirm) {
		this.roleConfirm = roleConfirm;
	}

	public UserStatusEnum getEnabledUser() {
		return enabledUser;
	}

	public void setEnabledUser(UserStatusEnum enabledUser) {
		this.enabledUser = enabledUser;
	}

	public boolean isAllowCreateRegister() {
		return allowCreateRegister;
	}

	public void setAllowCreateRegister(boolean allowCreateRegister) {
		this.allowCreateRegister = allowCreateRegister;
	}
}
