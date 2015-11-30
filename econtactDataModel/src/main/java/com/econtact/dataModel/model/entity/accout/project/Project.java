package com.econtact.dataModel.model.entity.accout.project;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractAuditeEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Entity
@Table(name = "project", schema = EntityHelper.E_CONTACT_SCHEMA, indexes = { @Index(name = "id_index_pk", columnList = EntityHelper.ID_F) })
@Audited
@AuditTable(value = "project_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
public class Project extends AbstractAuditeEntity<BigDecimal> {
	private static final long serialVersionUID = 6692176964337384451L;
	private static final String SEQ_NAME = "projectSeq";

	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_project_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	@Column(name = "name_project", nullable = false, length = 255)
	private String nameProject;

	@Column(name = "description_project", length = 1000)
	private String description;

	@ManyToOne
	@JoinColumn(name = "id_owner_fk")
	@NotAudited
	private SessionUserEntity owner;

	@Enumerated
	@Column(name = "confirm_create", nullable = false, precision = 1, scale = 0)
	private ConfirmStatusEnum confirmCreate;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
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

	public ConfirmStatusEnum getConfirmCreate() {
		return confirmCreate;
	}

	public void setConfirmCreate(ConfirmStatusEnum confirmCreate) {
		this.confirmCreate = confirmCreate;
	}

}
