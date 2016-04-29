package com.econtact.dataModel.model.entity.contact;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@Entity
@Table(name = "person", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.contact set sign = id where id = ? and version = ?")
public class PersonEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 6752840453310674593L;
	private static final String SEQ_NAME = "personSeq";

	/**
	 * Person id.
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_person_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * Person first name
	 */
	@Column(name = "first_name", length = 100)
	private String firstName;

	/**
	 * Person middle name
	 */
	@Column(name = "middle_name", length = 100)
	private String middleName;

	/**
	 * Person last name
	 */
	@Column(name = "last_name", length = 100)
	private String lastName;

	/**
	 * Person birthday
	 */
	@Column(name = "birthday")
	private Date birthday;

	/**
	 * Person sex. {@link UniverDictEntity} entity with {@link UniverDictEntity#getParamDict()} =
	 * {@link NamesDictConstant#PERSON_SEX}
	 */
	@ManyToOne
	@JoinColumn(name = "id_sex_uf_fk")
	@Fetch(FetchMode.SELECT)
	private UniverDictEntity sex;

	/**
	 * Person age range. {@link UniverDictEntity} entity with {@link UniverDictEntity#getParamDict()} =
	 * {@link NamesDictConstant#PERSON_AGE_RANGE}.
	 */
	@ManyToOne
	@JoinColumn(name = "id_age_range_ud_fk")
	@Fetch(FetchMode.SELECT)
	private UniverDictEntity ageRangeUd;

	/**
	 * Person status. {@link UniverDictEntity} entity with {@link UniverDictEntity#getParamDict()} =
	 * {@link NamesDictConstant#PERSON_STATUS}
	 */
	@ManyToOne
	@JoinColumn(name = "id_status_ud_fk")
	@Fetch(FetchMode.SELECT)
	private UniverDictEntity statusUd;

	/**
	 * Peson address {@link AddressEntity}
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_address_fk")
	private AddressEntity address;

	/**
	 * Person church {@link ChurchEntity}
	 */
	@ManyToOne
	@JoinColumn(name = "id_church_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
	private ChurchEntity church;

	/**
	 * Person contacts
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = ContactEntity.PERSON_A)
	private List<ContactEntity> contacts = new ArrayList<ContactEntity>();

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
	 * Method to return firstName
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method to set firstName
	 * 
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Method to return middleName
	 * 
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Method to set middleName
	 * 
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Method to return lastName
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method to set lastName
	 * 
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Method to return birthday
	 * 
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Method to set birthday
	 * 
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Method to return sex
	 * 
	 * @return the sex
	 */
	public UniverDictEntity getSex() {
		return sex;
	}

	/**
	 * Method to set sex
	 * 
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(UniverDictEntity sex) {
		this.sex = sex;
	}

	/**
	 * Method to return ageRangeUd
	 * 
	 * @return the ageRangeUd
	 */
	public UniverDictEntity getAgeRangeUd() {
		return ageRangeUd;
	}

	/**
	 * Method to set ageRangeUd
	 * 
	 * @param ageRangeUd
	 *            the ageRangeUd to set
	 */
	public void setAgeRangeUd(UniverDictEntity ageRangeUd) {
		this.ageRangeUd = ageRangeUd;
	}

	/**
	 * Method to return statusUd
	 * 
	 * @return the statusUd
	 */
	public UniverDictEntity getStatusUd() {
		return statusUd;
	}

	/**
	 * Method to set statusUd
	 * 
	 * @param statusUd
	 *            the statusUd to set
	 */
	public void setStatusUd(UniverDictEntity statusUd) {
		this.statusUd = statusUd;
	}

	/**
	 * Method to return address
	 * 
	 * @return the address
	 */
	public AddressEntity getAddress() {
		return address;
	}

	/**
	 * Method to set address
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	/**
	 * Method to return contacts
	 * 
	 * @return the contacts
	 */
	public List<ContactEntity> getContacts() {
		return contacts;
	}

	/**
	 * Method to set contacts
	 * 
	 * @param contacts
	 *            the contacts to set
	 */
	public void setContacts(List<ContactEntity> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Method to add contact to the person contacts
	 * 
	 * @param contact
	 *            - new contact
	 * @return - true - if contact will be added (like {@link Collection#add(Object)}), false otherwise.
	 */
	public boolean addContact(ContactEntity contact) {
		if (!this.contacts.contains(contact)) {
			contact.setPerson(this);
			return this.contacts.add(contact);
		}
		return false;
	}

	/**
	 * Methos to remove contact from the person contacts
	 * 
	 * @param contact
	 *            - contact to remove
	 * @return - true - if contact will be removed (like {@link Collection#remove(Object)}), false - ptherwise.
	 */
	public boolean removeContact(ContactEntity contact) {
		if (this.contacts.contains(contact)) {
			contact.setPerson(null);
			return this.contacts.remove(contact);
		}
		return false;
	}

	/**
	 * Method to return church
	 * 
	 * @return the church
	 */
	public ChurchEntity getChurch() {
		return church;
	}

	/**
	 * Method to set church
	 * 
	 * @param church
	 *            the church to set
	 */
	public void setChurch(ChurchEntity church) {
		this.church = church;
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
