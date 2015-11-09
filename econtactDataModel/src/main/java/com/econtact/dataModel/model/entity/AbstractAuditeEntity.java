package com.econtact.dataModel.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;

@MappedSuperclass
public abstract class AbstractAuditeEntity<PK extends Serializable> extends AbstractEntity<PK> {
	private static final long serialVersionUID = -8508773898839441954L;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

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
		setUpdData();
	}
	
	protected void setUpdData() {
		updAuthor = EJBContext.get().getUser().getUpdData();
		updDate = new Date();		
	}
}
