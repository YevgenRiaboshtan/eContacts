package com.econtact.dataModel.model.entity.access;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.ConstraintHelper;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

/**
 * Entity describes user {@link SessionUserEntity} permission to the church {@link ChurchEntity} entity.
 * Класс предоставления доступов пользователя к общине.
 * 
 * @author evgeniy
 *
 */
@Entity
@Table(name = "access_church", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = ConstraintHelper.ACCESS_CHURCH_NAME_SIGN, columnNames = {
		"id_user_fk", "id_church_fk", EntityHelper.SIGN_F }) })
@SQLDelete(sql = "UPDATE econtactschema.access_church set sign = id where id = ? and version = ?")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AccessChurchEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = -3925502235711757060L;
	private static final String SEQ_NAME = "accessChurchSeq";

	public static final String USER_A = "user";
	public static final String CHURCH_A = "church";
	public static final String CONFIRM_A = "confirm";
	public static final String VIEW_PERMIT_A = "viewPermit";
	public static final String EDIT_PERMIT_A = "editPermit";
	public static final String EDIT_GROUP_PERMIT_A = "editGroupPermit";

	/**
	 * Id
	 * Идентификатор записи
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_access_church_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * User {@link SessionUserEntity} that has permission.
	 * Пользователь {@link SessionUserEntity} которому предоставляется доступ к общине.
	 */
	@ManyToOne
	@JoinColumn(name = "id_user_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
	private SessionUserEntity user;

	/**
	 * Target Church {@link ChurchEntity}
	 * Община {@link ChurchEntity} к которой предоставляется доступ.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_church_fk", nullable = false)
	private ChurchEntity church;

	/**
	 * Access confirmation.
	 * Подтверждение предоставление доступа
	 */
	@Column(name = "confirm", nullable = false)
	private boolean confirm;

	/**
	 * Permission to view church information.
	 * Доступ на просмотр общины {@link ChurchEntity}
	 */
	@Column(name = "view_permit", nullable = false)
	private boolean viewPermit;

	/**
	 * Permission to edit church information.
	 * Доступ на редактирование информации об общине {@link ChurchEntity}
	 */
	@Column(name = "edit_permit", nullable = false)
	private boolean editPermit;

	/**
	 * Permission to add/remove users {@link SessionUserEntity} in the church {@link ChurchEntity}.
	 * Доступ на добавление/удаление пользователей {@link SessionUserEntity} в общине {@link ChurchEntity}.
	 */
	@Column(name = "edit_user_permit", nullable = false)
	private boolean editUserPermit;

	/**
	 * Permission to add/remove groups {@link GroupEntity} in the church {@link ChurchEntity}.
	 * Доступ на добавление/удаление групп {@link GroupEntity} в общине {@link ChurchEntity}.
	 */
	@Column(name = "edit_group_permit", nullable = false)
	private boolean editGroupPermit;

	/**
	 * Permission to add contacts in the church {@link ChurchEntity}.
	 * Доступ на добавление контактов в общине {@link ChurchEntity}.
	 */
	@Column(name = "add_contact_permit", nullable = false)
	private boolean addContactPermit;

	/**
	 * Permission to manage accesses {@link AccessChurchEntity} to the church {@link ChurchEntity}.
	 * Доступ на управление доступами {@link AccessChurchEntity} к общине {@link ChurchEntity}.
	 */
	@Column(name = "edit_access_permit", nullable = false)
	private boolean editAccessPermit;

	/**
	 * Permission to edit/delete contacts in the church {@link ChurchEntity}.
	 * Доступ на изменение/удаление контактов в общине {@link ChurchEntity}.
	 */
	@Column(name = "edit_contact_permit", nullable = false)
	private boolean editContactPermit;

	/**
	 * Entities last update date
	 * Дата последних изменений
	 */
	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	/**
	 * Author that provide last changes.
	 * Автор последних изменений.
	 */
	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	/**
	 * Actual field.
	 * Признак актуальности записи. 0 - запись актуальная, >0 - запись удалена.
	 */
	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	public BigDecimal getId() {
		return id;
	}

	/**
	 * Method set id value
	 * Устанавливает значение идентификатора
	 * @param id - id value
	 *  		- значение идентификатора
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * Return user wich provides access to the church.
	 * Возвращает пользователя которому предоставляется доступ
	 * @return - user {@link SessionUserEntity}
	 * 			- пользователь {@link SessionUserEntity}, которому предоставляется доступ
	 */
	public SessionUserEntity getUser() {
		return user;
	}

	/**
	 * Set user wich provides access to the church.
	 * Устанавливает пользователя которму предоставляется доступ
	 * @param user - user {@link SessionUserEntity}
	 * 				- пользователь {@link SessionUserEntity}
	 */
	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	/**
	 * Return target church to provides access.
	 * Возвращает общину которой предоставляется доступ
	 * @return - church {@link ChurchEntity}
	 * 			- община {@link ChurchEntity}
	 */
	public ChurchEntity getChurch() {
		return church;
	}

	/**
	 * Set target church to provides access.
	 * Устанавливает общину которой предоставляется доступ
	 * @param church - church {@link ChurchEntity}
	 * 				- община {@link ChurchEntity}
	 */
	public void setChurch(ChurchEntity church) {
		this.church = church;
	}

	
	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public boolean isViewPermit() {
		return viewPermit;
	}

	public void setViewPermit(boolean viewPermit) {
		this.viewPermit = viewPermit;
	}

	public boolean isEditPermit() {
		return editPermit;
	}

	public void setEditPermit(boolean editPermit) {
		this.editPermit = editPermit;
	}

	public boolean isEditUserPermit() {
		return editUserPermit;
	}

	public void setEditUserPermit(boolean editUserPermit) {
		this.editUserPermit = editUserPermit;
	}

	public boolean isEditGroupPermit() {
		return editGroupPermit;
	}

	public void setEditGroupPermit(boolean editGroupPermit) {
		this.editGroupPermit = editGroupPermit;
	}

	public boolean isAddContactPermit() {
		return addContactPermit;
	}

	public void setAddContactPermit(boolean addContactPermit) {
		this.addContactPermit = addContactPermit;
	}

	public boolean isEditAccessPermit() {
		return editAccessPermit;
	}

	public void setEditAccessPermit(boolean editAccessPermit) {
		this.editAccessPermit = editAccessPermit;
	}

	public boolean isEditContactPermit() {
		return editContactPermit;
	}

	public void setEditContactPermit(boolean editContactPermit) {
		this.editContactPermit = editContactPermit;
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

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdData();
	}

	@PreUpdate
	protected void setUpdData() {
		setUpdAuthor(EJBContext.get().getUser().getUpdData());
		setUpdDate(new Date());
	}
}
