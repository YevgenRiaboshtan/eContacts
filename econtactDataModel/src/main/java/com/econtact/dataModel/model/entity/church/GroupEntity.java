package com.econtact.dataModel.model.entity.church;

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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.AuditSupport;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Entity
@Table(name = "group", schema = EntityHelper.E_CONTACT_SCHEMA)
@Audited
@AuditTable(value = "group_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.group set sign = id where id = ? and version = ?")
public class GroupEntity extends AbstractEntity<BigDecimal> implements AuditSupport{
	private static final long serialVersionUID = -4535150141202020695L;
	private static final String SEQ_NAME = "churchSeq";
	private static final String NOTE_PATTERN = "Группа ID: '%s'";

	public static final String NAME_A = "name";
	public static final String DESCRIPTION_A = "description";
	public static final String CHURCH_A = "church";
	
	/**
	 * identifier of the group.
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_group_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * Group`s name
	 */
	@Column(name = "name", length = 200, nullable = false)
	private String name;

	/**
	 * Group`s description
	 */
	@Column(name = "description", length = 2000)
	private String description;

	//TODO investigate necessary this field. 
	/**
	 * Group`s owner
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_owner_fk", nullable = false)
	@NotAudited
	private SessionUserEntity owner;

	/**
	 * Church that contained group
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_church_fk", nullable = false)
	@NotAudited
	private ChurchEntity church;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	@Column(name = EntityHelper.SIGN_F, precision = 38, scale = 0, nullable = false)
	private BigDecimal sign;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SessionUserEntity getOwner() {
		return owner;
	}

	public void setOwner(SessionUserEntity owner) {
		this.owner = owner;
	}

	public ChurchEntity getChurch() {
		return church;
	}

	public void setChurch(ChurchEntity church) {
		this.church = church;
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

	@Override
	public String getEnversNote() {
		return String.format(NOTE_PATTERN, getId());
	}
}
