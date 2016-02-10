package com.econtact.dataModel.model.entity.accout;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AuditSupport;

@Entity
@Table(name = "user_account", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = AccountUserEntity.USER_LOGIN_SIGN_UNIQUE_CONSTRAINT, columnNames = {
		AbstractUserEntity.LOGIN_A, EntityHelper.SIGN_F }) }, indexes = {@Index(name = "user_login_index", columnList = AccountUserEntity.LOGIN_A) })
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Audited
@AuditTable(value = AccountUserAudView.TABLE_NAME, schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.user_account set sign = id where id = ? and version = ?")
public class AccountUserEntity extends AbstractUserEntity implements AuditSupport {
	private static final long serialVersionUID = -8588130700569489485L;
	private static final String NOTE_PATTERN = "Пользователь ID: '%s'";
	public static final String USER_LOGIN_SIGN_UNIQUE_CONSTRAINT = "user_login_sign_unique_constraint";
	public static final String ROLE_A = "role";
	public static final String PARENT_USER_A = "parentUser";

	/**
	 * Пароль пользователя в зашифрованном виде.
	 * Password of the User account Entity.
	 */
	@Column(name = "password", nullable = false, length = 100)
	private String password;

	/**
	 * Соль для шифрования пароля.
	 * Salt for check password of the User account Entity.
	 */
	@Column(name = "salt", nullable = false, length = 40)
	private String salt;

	/**
	 * Флаг подтверждения роли пользователя.
	 * Field for confirm role of the User account Entity. { @Link ConfirmStatusEnum}
	 */
	@Enumerated
	@Column(name = "role_confirm", nullable = false)
	private ConfirmStatusEnum roleConfirm;

	/**
	 * Статус пользователя Отключен или нет.
	 * Status of the User account. See {@link UserStatusEnum}
	 */
	@Enumerated
	@Column(name = "enabledUser", nullable = false, precision = 1, scale = 0)
	private UserStatusEnum enabledUser;

	/**
	 * Флаг разрешено ли создавать польззователей. true - разрешено.
	 * true - if user allowed create register users, false - disallowed.
	 */
	@Column(name = "allow_create_register", nullable = false)
	private boolean allowCreateRegister;

	/**
	 * Вышестоящий пользователь.
	 * Parent User. {@link SessionUserEntity}
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_parent_user_fk")
	@NotAudited
	private SessionUserEntity parentUser;

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

	public SessionUserEntity getParentUser() {
		return parentUser;
	}

	public void setParentUser(SessionUserEntity parentUser) {
		this.parentUser = parentUser;
	}

	public boolean isEnablesAccount() {
		return UserStatusEnum.ENABLE.equals(enabledUser);
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
