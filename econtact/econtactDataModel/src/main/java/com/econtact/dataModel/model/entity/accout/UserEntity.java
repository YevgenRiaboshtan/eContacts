package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Audited
@AuditTable(value = "user_account_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE " + EntityHelper.E_CONTACT_SCHEMA
		+ ".user_account SET sign = id WHERE id = ? AND version = ?")
public class UserEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 1L;
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

	@Column(name = "pass", nullable = false, length = 100)
	private String pass;

	@Column(name = "salt", nullable = false, length = 29)
	private String salt;

	@Column(name = "disable", nullable = false, precision = 1)
	private Boolean disable;

	@Column(name = "upd_date", nullable = false)
	private Date updDate;

	@Column(name = "upd_author", nullable = false, length = 238)
	private String updAuthor;

	@Column(name = "sign", nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	@Column(name = "is_admin", nullable = false, precision = 1)
	private Boolean isAdmin = false;

	@ElementCollection(targetClass=RoleType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_roles")
    @Column(name="role_name")
	@Fetch(FetchMode.SELECT)
	private Set<RoleType> roles;

	public UserEntity() {
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean isDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdAuthor() {
		return updAuthor;
	}

	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	public BigDecimal getSign() {
		return sign;
	}

	public void setSign(BigDecimal sign) {
		this.sign = sign;
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUpdInfo() {
		return getId() + " " + getFirstName() + " " + getLastName();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Set<RoleType> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleType> roles) {
		this.roles = roles;
	}

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdData();
	}

	@PreUpdate
	@PreRemove
	public void setUpdData() {
		setUpdDate(new Date());
		EJBContext.get().getUser().getUpdInfo();
	}
}
