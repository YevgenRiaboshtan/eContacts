package com.econtact.dataModel.model.entity.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.econtact.dataModel.data.listeners.AuditRevListener;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

/**
 * Описывает сущность ревизии фискального аудита. see also
 * http://lukaszantoniak.wordpress.com/2011/07/02/hhh-5580/
 * 
 * @author evgeniy
 *
 */

@Entity
@RevisionEntity(AuditRevListener.class)
@Table(name = "audit_rev", schema = EntityHelper.E_CONTACT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AuditRevEntity implements AbstractView<Long> {
	/**
	 * @defaultUID
	 */
	private static final long serialVersionUID = 1L;
	private static final String SEQ_NAME = "auditRevSeq";

	@SequenceGenerator(name = SEQ_NAME, sequenceName = "s$audit_rev", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@Id
	@RevisionNumber
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, nullable = false)
	private Long id;

	@RevisionTimestamp
	@Column(name = "time_stamp")
	private long timestamp;

	@Column(name = "date_ev", nullable = false)
	private Date dateEv;

	@Column(name = "name_ev", nullable = true, length = 300)
	private String nameEv;

	@Column(name = "note", nullable = true, length = 1000)
	private String note;

	@ManyToOne
	@JoinColumn(name = "id_event_ud", nullable = false)
	@Fetch(FetchMode.SELECT)
	private UniverDictEntity event;

	@ManyToOne
	@JoinColumn(name = "id_user_fk")
	@NotAudited
	private SessionUserEntity user;

	public SessionUserEntity getUser() {
		return user;
	}

	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	@OneToMany(mappedBy = AuditRevChangedEntity.AUDIT_REV_A, cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	private List<AuditRevChangedEntity> changesEntities = new ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDateEv() {
		return dateEv;
	}

	public void setDateEv(Date dateEv) {
		this.dateEv = dateEv;
	}

	public String getNameEv() {
		return nameEv;
	}

	public void setNameEv(String nameEv) {
		this.nameEv = nameEv;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UniverDictEntity getEvent() {
		return event;
	}

	public void setEvent(UniverDictEntity event) {
		this.event = event;
	}

	public void addChangesEntity(final AuditRevChangedEntity changesEntity) {
		if (!changesEntities.contains(changesEntity)) {
			changesEntity.setAuditRev(this);
			changesEntities.add(changesEntity);
		}
	}
}
