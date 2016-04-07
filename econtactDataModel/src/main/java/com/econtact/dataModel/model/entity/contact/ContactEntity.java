package com.econtact.dataModel.model.entity.contact;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

/**
 * Contact class. typeUd - type of the contact, describe by church administrator.
 * 
 * @author Yevgen Riaboshtan
 *
 */
@Entity
@Table(name = "contact", schema = EntityHelper.E_CONTACT_SCHEMA)
public class ContactEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 1194096729520946982L;
	private static final String SEQ_NAME = "contactSeq";

	public static final String PERSON_A = "person";
	
	/**
	 * Contacts id.
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_contact_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * Person {@link PersonEntity} - contact owner.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_person_fk", nullable = false)
	private PersonEntity person;

	/**
	 * {@link UniverDictEntity} - contact type.
	 */
	@ManyToOne
	@JoinColumn(name = "id_type_ud_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
	private UniverDictEntity typeUd;

	/**
	 * Contact value. Phone number or email or etc.
	 */
	@Column(name = "value")
	private String value;

	/**
	 * Last update date.
	 */
	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	/**
	 * Last update author.
	 */
	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	/**
	 * Field for mark row as deleted. If sign = 0 actual row, if sign = id - row is delete.
	 */
	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

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
	 * Method to return person
	 * 
	 * @return the person
	 */
	public PersonEntity getPerson() {
		return person;
	}

	/**
	 * Method to set person
	 * 
	 * @param person
	 *            the person to set
	 */
	public void setPerson(PersonEntity person) {
		this.person = person;
	}

	/**
	 * Method to return typeUd
	 * 
	 * @return the typeUd
	 */
	public UniverDictEntity getTypeUd() {
		return typeUd;
	}

	/**
	 * Method to set typeUd
	 * 
	 * @param typeUd
	 *            the typeUd to set
	 */
	public void setTypeUd(UniverDictEntity typeUd) {
		this.typeUd = typeUd;
	}

	/**
	 * Method to return value
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Method to set value
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Method to return updDate
	 * 
	 * @return the updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * Method to set updDate
	 * 
	 * @param updDate
	 *            the updDate to set
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * Method to return updAuthor
	 * 
	 * @return the updAuthor
	 */
	public String getUpdAuthor() {
		return updAuthor;
	}

	/**
	 * Method to set updAuthor
	 * 
	 * @param updAuthor
	 *            the updAuthor to set
	 */
	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	/**
	 * Method to return sign
	 * 
	 * @return the sign
	 */
	public BigDecimal getSign() {
		return sign;
	}

	/**
	 * Method to set sign
	 * 
	 * @param sign
	 *            the sign to set
	 */
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
