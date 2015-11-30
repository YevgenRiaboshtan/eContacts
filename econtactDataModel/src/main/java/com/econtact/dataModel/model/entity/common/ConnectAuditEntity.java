package com.econtact.dataModel.model.entity.common;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@Entity
@Table(name = "connect_audit", schema = EntityHelper.E_CONTACT_SCHEMA, indexes = { @Index(name = "id_index_pk", columnList = EntityHelper.ID_A) })
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class ConnectAuditEntity implements AbstractView<BigDecimal> {
	private static final long serialVersionUID = 3098825264209644798L;
	private static final String EMPTY_VALUE = "--";
	private static final String SEQ_NAME = "connectAuditSeq";
	public static final String DATE_CON_A = "dateCon";
	public static final String USER_A = "user";

	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_connect_audit_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	@Column(name = "name_os", nullable = true, length = 100)
	private String nameOs;

	@Column(name = "name_comp", nullable = false, length = 100)
	private String nameComp;

	@Column(name = "date_con", nullable = false)
	private Date dateCon;

	@Column(name = "prog", nullable = true, length = 500)
	private String prog;

	@Column(name = "ip_addr", nullable = false, length = 41)
	private String ipAddr;

	@ManyToOne
	@JoinColumn(name = "id_action_ud", nullable = false)
	@Fetch(FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private UniverDictEntity idActionUd;

	@ManyToOne
	@JoinColumn(name = "id_user_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
	private SessionUserEntity user;

	@Override
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getNameOs() {
		return nameOs;
	}

	public void setNameOs(String nameOs) {
		this.nameOs = nameOs;
	}

	public String getNameComp() {
		return nameComp;
	}

	public void setNameComp(String nameComp) {
		this.nameComp = nameComp;
	}

	public Date getDateCon() {
		return dateCon;
	}

	public void setDateCon(Date dateCon) {
		this.dateCon = dateCon;
	}

	public String getProg() {
		return prog;
	}

	public void setProg(String prog) {
		this.prog = prog;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public UniverDictEntity getIdActionUd() {
		return idActionUd;
	}

	public void setIdActionUd(UniverDictEntity idActionUd) {
		this.idActionUd = idActionUd;
	}

	public SessionUserEntity getUser() {
		return user;
	}

	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	public static ConnectAuditEntity create(final SessionUserEntity user, final UniverDictEntity idActionUd,
			final String prog, final String ipAddr) {
		final ConnectAuditEntity result = new ConnectAuditEntity();
		result.setUser(user);
		result.setIdActionUd(idActionUd);
		result.setProg(prog);
		result.setIpAddr(ipAddr);
		return result;
	}

	@PrePersist
	public void prePersist() {
		id = BigDecimal.ZERO;
		dateCon = new Date();
		if (nameComp == null) {
			nameComp = EMPTY_VALUE;
		}
		if (nameOs == null) {
			nameOs = EMPTY_VALUE;
		}
		if (prog == null) {
			prog = EMPTY_VALUE;
		}
		if (ipAddr == null) {
			ipAddr = "Session expired";
		}
	}
}
