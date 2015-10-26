package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

@MappedSuperclass
public abstract class AbstractUserEntity extends AbstractEntity<BigDecimal>{
	private static final long serialVersionUID = 6639216171054951488L;
	private static final String SEQ_NAME = "userSeq";
	
	public final static String LOGIN_A = "login";
	
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_user_id", schema = EntityHelper.E_CONTACT_SCHEMA)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	@Column(name = "login", nullable = false, length = 100)
	private String login;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

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
	
	@PrePersist
	public void prePersist() {
		sign = EntityHelper.ACTUAL_SIGN;
		updAuthor = EJBContext.get().getUser().getUpdData();
		updDate = new Date();
	}
}
