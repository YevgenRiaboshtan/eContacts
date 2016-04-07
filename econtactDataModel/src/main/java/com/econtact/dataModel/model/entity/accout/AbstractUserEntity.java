package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

@MappedSuperclass
@Audited
public abstract class AbstractUserEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 6639216171054951488L;
	private static final String SEQ_NAME = "userSeq";

	public final static String LOGIN_A = "login";

	/**
	 * Identifier of the User Entity.
	 */
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_user_id", schema = EntityHelper.E_CONTACT_SCHEMA)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	@Access(AccessType.PROPERTY)
	private BigDecimal id;

	/**
	 * Login
	 */
	@Column(name = "login", nullable = false, length = 100)
	private String login;
	
	/**
	 * Accounts Role - {@link RoleType}
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private RoleType role;

	/**
	 * Field for mark row is deleted. If sign = 0 actual row, if sign = id - row is deleted.
	 */
	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;
	
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

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
	
	/**
	 * Method to return sign 
	 * @return the sign
	 */
	public BigDecimal getSign() {
		return sign;
	}

	/**
	 * Method to set sign
	 * @param sign the sign to set
	 */
	public void setSign(BigDecimal sign) {
		this.sign = sign;
	}

}
