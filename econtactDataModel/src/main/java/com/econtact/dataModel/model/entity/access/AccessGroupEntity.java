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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.GroupEntity;

@Entity
@Table(name = "access_group", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.access_group set sign = id where id = ? and version = ?")
@NamedEntityGraphs({ @NamedEntityGraph(name = AccessGroupEntity.ACCESS_GROUP_GRAPH, attributeNodes = { @NamedAttributeNode(AccessGroupEntity.GROUP_A) }) })
public class AccessGroupEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = -7154728264123207296L;
	private static final String SEQ_NAME = "accessGroupSeq";

	public static final String ACCESS_GROUP_GRAPH = "accessGroupGraph";

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
	@ManyToOne
	@JoinColumn(name = "id_user_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
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
	@Column(name = "view_permit", nullable = false)
	private boolean viewPermit;

	/**
	 * Разрешение на редактирование
	 */
	@Column(name = "edit_permit", nullable = false)
	private boolean editPermit;

	/**
	 * Разрешение на регистрацию контактов в группе.
	 */
	@Column(name = "register_permit", nullable = false)
	private boolean registerPermit;

	/**
	 * Подтверждение разрешения
	 */
	@Column(name = "confirm", nullable = false)
	private boolean confirm;

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

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public boolean isRegisterPermit() {
		return registerPermit;
	}

	public void setRegisterPermit(boolean registerPermit) {
		this.registerPermit = registerPermit;
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
