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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Entity
@Table(name = "connect_audit", schema = EntityHelper.E_CONTACT_SCHEMA, indexes = { @Index(columnList = "sessionId")})
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class ConnectAuditEntity implements AbstractView<BigDecimal> {
	private static final long serialVersionUID = 3098825264209644798L;
	private static final String SEQ_NAME = "connectAuditSeq";
	public static final String END_VISIT_A = "endVisit";
	public static final String SESSION_ID_A = "sessionId";

	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_connect_audit_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	@Column(name = "sessionId", nullable = false, length = 100)
	private String sessionId;

	@Column(name = "startVisit", nullable = false)
	private Date startVisit;

	@Column(name = "endVisit")
	private Date endVisit;

	@Column(name = "ipAddress", nullable = false, length = 100)
	private String ipAddress;

	@Column(name = "deviceName", nullable = false, length = 500)
	private String deviceName;

	@ManyToOne
	@JoinColumn(name = "id_user_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
	private SessionUserEntity user;

	/**
	 * Method to return id
	 * 
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * Method to set id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * Method to return sessionId
	 * 
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Method to set sessionId
	 * 
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Method to return startVisit
	 * 
	 * @return the startVisit
	 */
	public Date getStartVisit() {
		return startVisit;
	}

	/**
	 * Method to set startVisit
	 * 
	 * @param startVisit
	 *            the startVisit to set
	 */
	public void setStartVisit(Date startVisit) {
		this.startVisit = startVisit;
	}

	/**
	 * Method to return endVisit
	 * 
	 * @return the endVisit
	 */
	public Date getEndVisit() {
		return endVisit;
	}

	/**
	 * Method to set endVisit
	 * 
	 * @param endVisit
	 *            the endVisit to set
	 */
	public void setEndVisit(Date endVisit) {
		this.endVisit = endVisit;
	}

	/**
	 * Method to return ipAddress
	 * 
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Method to set ipAddress
	 * 
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Method to return deviceName
	 * 
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Method to set deviceName
	 * 
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Method to return user
	 * 
	 * @return the user
	 */
	public SessionUserEntity getUser() {
		return user;
	}

	/**
	 * Method to set user
	 * 
	 * @param user
	 *            the user to set
	 */
	public void setUser(SessionUserEntity user) {
		this.user = user;
	}

	public static ConnectAuditEntity create(final SessionUserEntity user, final String sessionId,
			final String ipAddress, final String deviceName) {
		final ConnectAuditEntity result = new ConnectAuditEntity();
		result.setSessionId(sessionId);
		result.setStartVisit(new Date());
		result.setUser(user);
		result.setIpAddress(ipAddress);
		result.setDeviceName(deviceName);
		return result;
	}
}
