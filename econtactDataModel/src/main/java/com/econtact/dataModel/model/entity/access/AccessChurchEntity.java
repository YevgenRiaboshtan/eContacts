package com.econtact.dataModel.model.entity.access;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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

import org.hibernate.annotations.SQLDelete;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

/**
 * Сущность предоставление доступа пользователю к общине.
 * 
 * @author evgeniy
 *
 */
@Entity
@Table(name = "access_church", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = "user_church_unique", columnNames = {
		"id_user_fk", "id_church_fk", EntityHelper.SIGN_F }) })
@SQLDelete(sql = "UPDATE econtactschema.access_church set sign = id where id = ? and version = ?")
public class AccessChurchEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = -3925502235711757060L;
	private static final String SEQ_NAME = "accessChurchSeq";

	public static final String USER_A = "user";
	public static final String CHURCH_A = "church";
	public static final String CONFIRM_A = "confirm";

	/**
	 * идентификатор доступа
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_access_church_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * Пользователь {@link SessionUserEntity} которому предоставляется доступ к общине
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_fk", nullable = false)
	private SessionUserEntity user;

	/**
	 * Община {@link ChurchEntity} к которой предоставляется доступ
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_church_fk", nullable = false)
	private ChurchEntity church;

	/**
	 * Подтверждение предоставленого доступа
	 */
	@Enumerated
	@Column(name = "confirm", nullable = false)
	private ConfirmStatusEnum confirm;

	/**
	 * Доступ на просмотр общины {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "view_permit", nullable = false)
	private ConfirmStatusEnum viewPermit;

	/**
	 * Доступ на редактирование информации об общине {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "edit_permit", nullable = false)
	private ConfirmStatusEnum editPermit;

	/**
	 * Доступ на добавление/удаление пользователей {@link SessionUserEntity} в общине {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "edit_user_permit", nullable = false)
	private ConfirmStatusEnum editUserPermit;

	/**
	 * Доступ на добавление/удаление групп {@link GroupEntity} в общине {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "edit_group_permit", nullable = false)
	private ConfirmStatusEnum editGroupPermit;

	/**
	 * Доступ на добавление контактов в общине {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "add_contact_permit", nullable = false)
	private ConfirmStatusEnum addContactPermit;

	/**
	 * Доступ на руправление доступами {@link AccessChurchEntity} к общине {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "adit_access_permit", nullable = false)
	private ConfirmStatusEnum editAccessPermit;
	/**
	 * Доступ на изменение/удаление контактов в общине {@link ChurchEntity}
	 */
	@Enumerated
	@Column(name = "edit_contact_permit", nullable = false)
	private ConfirmStatusEnum editContactPermit;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public SessionUserEntity getUser() {
		return user;
	}

	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	public ChurchEntity getChurch() {
		return church;
	}

	public void setChurch(ChurchEntity church) {
		this.church = church;
	}

	public ConfirmStatusEnum getConfirm() {
		return confirm;
	}

	public void setConfirm(ConfirmStatusEnum confirm) {
		this.confirm = confirm;
	}

	public ConfirmStatusEnum getViewPermit() {
		return viewPermit;
	}

	public void setViewPermit(ConfirmStatusEnum viewPermit) {
		this.viewPermit = viewPermit;
	}

	public ConfirmStatusEnum getEditPermit() {
		return editPermit;
	}

	public void setEditPermit(ConfirmStatusEnum editPermit) {
		this.editPermit = editPermit;
	}

	public ConfirmStatusEnum getEditUserPermit() {
		return editUserPermit;
	}

	public void setEditUserPermit(ConfirmStatusEnum editUserPermit) {
		this.editUserPermit = editUserPermit;
	}

	public ConfirmStatusEnum getEditGroupPermit() {
		return editGroupPermit;
	}

	public void setEditGroupPermit(ConfirmStatusEnum editGroupPermit) {
		this.editGroupPermit = editGroupPermit;
	}

	public ConfirmStatusEnum getAddContactPermit() {
		return addContactPermit;
	}

	public void setAddContactPermit(ConfirmStatusEnum addContactPermit) {
		this.addContactPermit = addContactPermit;
	}

	public ConfirmStatusEnum getEditContactPermit() {
		return editContactPermit;
	}

	public void setEditContactPermit(ConfirmStatusEnum editContactPermit) {
		this.editContactPermit = editContactPermit;
	}

	public ConfirmStatusEnum getEditAccessPermit() {
		return editAccessPermit;
	}

	public void setEditAccessPermit(ConfirmStatusEnum editAccessPermit) {
		this.editAccessPermit = editAccessPermit;
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