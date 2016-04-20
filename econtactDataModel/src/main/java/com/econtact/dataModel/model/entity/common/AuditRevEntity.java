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

/**
 * Описывает сущность ревизии аудита. see also
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

	/**
	 * Идентификатор
	 */
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_audit_rev", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@Id
	@RevisionNumber
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, nullable = false)
	private Long id;

	/**
	 * Время ревизии
	 */
	@RevisionTimestamp
	@Column(name = "time_stamp")
	private long timestamp;

	/**
	 * Дата события.
	 */
	@Column(name = "date_ev", nullable = false)
	private Date dateEv;

	/**
	 * Название события.
	 */
	@Column(name = "name_ev", nullable = true, length = 300)
	private String nameEv;
	
	/**
	 * Описание события.
	 */
	@Column(name = "note", nullable = true, length = 1000)
	private String note;

	/**
	 * Пользователь {@link SessionUserEntity} который изменяет сущность.
	 */
	@ManyToOne
	@JoinColumn(name = "id_user_fk")
	@NotAudited
	@Fetch(FetchMode.SELECT)
	private SessionUserEntity user;
	
	/**
	 * Список зависимых изменяемых объектов.
	 */
	@OneToMany(mappedBy = AuditRevChangedEntity.AUDIT_REV_A, cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	private List<AuditRevChangedEntity> changesEntities = new ArrayList<>();

	/**
	 * Получение пользователя {@link SessionUserEntity} производившего изменения
	 * @return - {@link SessionUserEntity} - пользователь производивший изменения
	 */
	public SessionUserEntity getUser() {
		return user;
	}

	/**
	 * Установка пользователя {@link SessionUserEntity} производившего изменения
	 * @param user - {@link SessionUserEntity} - пользователь системы.
	 */
	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Установка Идентификатора
	 * @param id - идентификатор
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Получение времени события
	 * @return - время события
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Установка времени события
	 * @param timestamp - время события
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Получение даты события
	 * @return - дата события
	 */
	public Date getDateEv() {
		return dateEv;
	}

	/**
	 * Установка даты события
	 * @param dateEv - дата события
	 */
	public void setDateEv(Date dateEv) {
		this.dateEv = dateEv;
	}

	/**
	 * Установка названия события
	 * @return - название события
	 */
	public String getNameEv() {
		return nameEv;
	}

	/**
	 * Установка названия события
	 * @param nameEv - название события
	 */
	public void setNameEv(String nameEv) {
		this.nameEv = nameEv;
	}

	/**
	 * Установка описания события
	 * @return - описание события
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Установка описания события
	 * @param note - описание события
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Добавление зависимого изменяемого объекта
	 * @param changesEntity - изменяемы объект
	 */
	public void addChangesEntity(final AuditRevChangedEntity changesEntity) {
		if (!changesEntities.contains(changesEntity)) {
			changesEntity.setAuditRev(this);
			changesEntities.add(changesEntity);
		}
	}
}
