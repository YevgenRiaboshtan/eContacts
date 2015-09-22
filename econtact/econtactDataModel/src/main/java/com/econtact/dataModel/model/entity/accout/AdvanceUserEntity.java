package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.econtact.dataModel.data.util.EntityHelper;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class AdvanceUserEntity extends AbstractUserEntity {
	private static final long serialVersionUID = 5694870185179400515L;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "salt", nullable = false, length = 20)
	private String salt;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

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
	
	@PrePersist
	public void prePersist(){
		sign = EntityHelper.ACTUAL_SIGN;
		updAuthor = "System";
		updDate = new Date();
	}
	
}
