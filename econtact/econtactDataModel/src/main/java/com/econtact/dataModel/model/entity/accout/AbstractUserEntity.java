package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

@MappedSuperclass
public abstract class AbstractUserEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 5524076410143768082L;
	private static final String SEQ_NAME = "userSeq";

	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_user_id", schema = EntityHelper.E_CONTACT_SCHEMA)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	@Column(name = "login", nullable = false, length = 100, unique = true)
	private String login;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@ElementCollection(targetClass = RoleType.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_account_roles")
	@Column(name = "role_name")
	@Fetch(FetchMode.SELECT)
	private Set<RoleType> roles = new HashSet<>();

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addRole(RoleType role) {
		if (!roles.contains(role)) {
			roles.add(role);
		}
	}

	public void removeRole(RoleType role) {
		if (roles.contains(role)) {
			roles.remove(role);
		}
	}
}
