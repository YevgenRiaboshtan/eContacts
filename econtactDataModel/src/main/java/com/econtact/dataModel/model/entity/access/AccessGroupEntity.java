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

import org.hibernate.annotations.SQLDelete;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

@Entity
@Table(name = "access_group", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.access_group set sign = id where id = ? and version = ?")
public class AccessGroupEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = -7154728264123207296L;
	private static final String SEQ_NAME = "accessGroupSeq";
	
	public static final String USER_A = "user";
	public static final String GROUP_A = "group";
	public static final String CONFIRM_A = "confirm";
	public static final String VIEW_PERMIT_A = "viewPermit";
	public static final String EDIT_PERMIT_A = "editPermit";

	/**
	 * identifier of the access.
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_access_group_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * Пользователь которому предоставляется доступ
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_fk", nullable = false)
	private SessionUserEntity user;

	/**
	 * Группа которой предоставвляется доступ
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_group_fk", nullable = false)
	private GroupEntity group;

	/**
	 * Разрешение на просмотр
	 */
	@Enumerated
	@Column(name = "view_permit", nullable = false)
	private ConfirmStatusEnum viewPermit;

	/**
	 * Разрешение на редактирование
	 */
	@Enumerated
	@Column(name = "edit_permit", nullable = false)
	private ConfirmStatusEnum editPermit;

	/**
	 * Подтверждение разрешения
	 */
	@Enumerated
	@Column(name = "confirm", nullable = false)
	private ConfirmStatusEnum confirm;

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

	public SessionUserEntity getUser() {
		return user;
	}

	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	public GroupEntity getGroup() {
		return group;
	}

	public void setGroup(GroupEntity group) {
		this.group = group;
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

	public ConfirmStatusEnum getConfirm() {
		return confirm;
	}

	public void setConfirm(ConfirmStatusEnum confirm) {
		this.confirm = confirm;
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
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdData();
	}

	@PreUpdate
	protected void setUpdData() {
		setUpdAuthor(EJBContext.get().getUser().getUpdData());
		setUpdDate(new Date());
	}
}
