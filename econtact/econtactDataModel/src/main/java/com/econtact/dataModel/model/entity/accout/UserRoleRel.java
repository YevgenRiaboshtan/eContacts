package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

@Entity
@Table(name = "user_role_relation", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "Update user_role_relation SET sign = id WHERE id = ? AND version=?")
public class UserRoleRel extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = -395100601125751500L;
	private static final String SEQ_NAME = "userRoleRelSeq";

	public static final String USER_A = "user";
	public static final String ROLE_A = "role";
	public static final String CONFIRM_A = "confirm";

	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_user_role_rel_id", schema = EntityHelper.E_CONTACT_SCHEMA)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = EntityHelper.ID_F, nullable = false)
	private AdvanceUserEntity user;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private RoleType role;

	@Enumerated
	@Column(name = "confirm", nullable = false, precision = 1, scale = 0)
	private AccessStatusEnum confirm;

	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public AdvanceUserEntity getUser() {
		return user;
	}

	public void setUser(AdvanceUserEntity user) {
		this.user = user;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public AccessStatusEnum getConfirm() {
		return confirm;
	}

	public void setConfirm(AccessStatusEnum confirm) {
		this.confirm = confirm;
	}

	public BigDecimal getSign() {
		return sign;
	}

	public void setSign(BigDecimal sign) {
		this.sign = sign;
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

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdData();
	}

	@PreUpdate
	@PreRemove
	public void setUpdData() {
		setUpdAuthor(EJBContext.get().getUser().getUpdData());
		setUpdDate(new Date());
	}
}
