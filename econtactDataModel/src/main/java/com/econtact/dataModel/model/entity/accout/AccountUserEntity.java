package com.econtact.dataModel.model.entity.accout;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AuditSupport;
import com.econtact.dataModel.model.entity.ConstraintHelper;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = ConstraintHelper.ACCOUNT_USER_LOGIN_SIGN, columnNames = {
		AbstractUserEntity.LOGIN_A, EntityHelper.SIGN_F }) }, indexes = { @Index(name = ConstraintHelper.ACCOUNT_USER_LOGIN_INDEX, columnList = AccountUserEntity.LOGIN_A) })
@Audited
@AuditTable(value = "user_account_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.user_account set sign = id where id = ? and version = ?")
@NamedEntityGraph(name = AccountUserEntity.ACCOUNT_PARENT_GRAPH, attributeNodes = @NamedAttributeNode(AccountUserEntity.PARENT_USER_A))
public class AccountUserEntity extends AbstractUserEntity implements AuditSupport {
	private static final long serialVersionUID = -8588130700569489485L;
	private static final String NOTE_PATTERN = "Пользователь ID: '%s'";

	public static final String ACCOUNT_PARENT_GRAPH = "account.parentUser.graph";

	public static final String ROLE_A = "role";
	public static final String PARENT_USER_A = "parentUser";

	/**
	 * Фамилия пользователя First name of the User Entity.
	 */
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	/**
	 * Имя пользователя. Last name of the User Entity.
	 */
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	
	/**
	 * Пароль пользователя в зашифрованном виде. Password of the User account Entity.
	 */
	@Column(name = "password", nullable = false, length = 100)
	private String password;

	/**
	 * Соль для шифрования пароля. Salt for check password of the User account Entity.
	 */
	@Column(name = "salt", nullable = false, length = 40)
	private String salt;

	/**
	 * Флаг подтверждения роли пользователя. Field for confirm role of the User account Entity. { @Link
	 * ConfirmStatusEnum}
	 */
	@Enumerated
	@Column(name = "role_confirm", nullable = false)
	private ConfirmStatusEnum roleConfirm;

	/**
	 * Статус пользователя Отключен или нет. Status of the User account. See {@link UserStatusEnum}
	 */
	@Enumerated
	@Column(name = "enabledUser", nullable = false, precision = 1, scale = 0)
	private UserStatusEnum enabledUser;

	/**
	 * Флаг разрешено ли создавать польззователей. true - разрешено. true - if user allowed create register users, false
	 * - disallowed.
	 */
	@Column(name = "allow_create_register", nullable = false)
	private boolean allowCreateRegister;

	/**
	 * Вышестоящий пользователь. Parent User. {@link SessionUserEntity}
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_parent_user_fk")
	@NotAudited
	private SessionUserEntity parentUser;

	/**
	 * Last update author.
	 */
	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	/**
	 * Last update date.
	 */
	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	/**
	 * Field for mark row is deleted. If sign = 0 actual row, if sign = id - row is deleted.
	 */
	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;
	
	/**
	 * Method to return firstName 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method to set firstName
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Method to return lastName 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method to set lastName
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Method to return password 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method to set password
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Method to return salt 
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Method to set salt
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Method to return roleConfirm 
	 * @return the roleConfirm
	 */
	public ConfirmStatusEnum getRoleConfirm() {
		return roleConfirm;
	}

	/**
	 * Method to set roleConfirm
	 * @param roleConfirm the roleConfirm to set
	 */
	public void setRoleConfirm(ConfirmStatusEnum roleConfirm) {
		this.roleConfirm = roleConfirm;
	}

	/**
	 * Method to return enabledUser 
	 * @return the enabledUser
	 */
	public UserStatusEnum getEnabledUser() {
		return enabledUser;
	}

	/**
	 * Method to set enabledUser
	 * @param enabledUser the enabledUser to set
	 */
	public void setEnabledUser(UserStatusEnum enabledUser) {
		this.enabledUser = enabledUser;
	}

	/**
	 * Method to return parentUser 
	 * @return the parentUser
	 */
	public SessionUserEntity getParentUser() {
		return parentUser;
	}

	/**
	 * Method to set parentUser
	 * @param parentUser the parentUser to set
	 */
	public void setParentUser(SessionUserEntity parentUser) {
		this.parentUser = parentUser;
	}

	/**
	 * Method to return updAuthor 
	 * @return the updAuthor
	 */
	public String getUpdAuthor() {
		return updAuthor;
	}

	/**
	 * Method to set updAuthor
	 * @param updAuthor the updAuthor to set
	 */
	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	/**
	 * Method to return updDate 
	 * @return the updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * Method to set updDate
	 * @param updDate the updDate to set
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
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

	/**
	 * Method to return allowCreateRegister 
	 * @return the allowCreateRegister
	 */
	public boolean isAllowCreateRegister() {
		return allowCreateRegister;
	}

	/**
	 * Method to set allowCreateRegister
	 * @param allowCreateRegister the allowCreateRegister to set
	 */
	public void setAllowCreateRegister(boolean allowCreateRegister) {
		this.allowCreateRegister = allowCreateRegister;
	}

	@PreUpdate
	public void preUpdate() {
		setUpdData();
	}

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		if (RoleType.ROLE_SUPER_ADMIN.equals(getRole())) {
			setUpdAuthor("default System Creation");
			setUpdDate(new Date());
		} else {
			setUpdData();
		}
	}

	protected void setUpdData() {
		setUpdAuthor(EJBContext.get().getUser().getUpdData());
		setUpdDate(new Date());
	}

	@Override
	public String getEnversNote() {
		return String.format(NOTE_PATTERN, getId());
	}
}
