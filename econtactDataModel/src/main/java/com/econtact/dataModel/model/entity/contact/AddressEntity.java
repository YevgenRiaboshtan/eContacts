package com.econtact.dataModel.model.entity.contact;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.SQLDelete;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

/**
 * Class represent address information of the Person {@link PersonEntity}.
 * @author Yevgen Riaboshtan
 *
 */
@Entity
@Table(name = "address", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.address set sign = id where id = ? and version = ?")
public class AddressEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = -6441206697904776733L;
	private static final String SEQ_NAME = "addressSeq";
	/**
	 * Person address id.
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_address_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;
	
	/**
	 * Person address country
	 */
	@Column(name = "country", length = 200)
	private String country;

	/**
	 * Person address state
	 */
	@Column(name = "state", length = 200)
	private String state;

	/**
	 * Person address city
	 */
	@Column(name = "city", length = 200)
	private String city;

	/**
	 * Person address city region
	 */
	@Column(name = "region", length = 200)
	private String region;
	
	/**
	 * Person address street
	 */
	@Column(name = "street", length = 200)
	private String street;

	/**
	 * Person address house number
	 */
	@Column(name = "number", length = 50)
	private String number;

	/**
	 * Person address flat number
	 */
	@Column(name = "flat", length = 50)
	private String flat;
	
	/**
	 * ZIP code
	 */
	@Column(name = "zip", length = 50)
	private String zip;
	
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
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * Method to set id
	 * @param id the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * Method to return country 
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Method to set country
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Method to return state 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Method to set state
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Method to return city 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Method to set city
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Method to return region 
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Method to set region
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Method to return street 
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Method to set street
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Method to return number 
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Method to set number
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Method to return flat 
	 * @return the flat
	 */
	public String getFlat() {
		return flat;
	}

	/**
	 * Method to set flat
	 * @param flat the flat to set
	 */
	public void setFlat(String flat) {
		this.flat = flat;
	}

	/**
	 * Method to return zip 
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Method to set zip
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Method to return updDate 
	 * @return the updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * Method to set updDate
	 * @param updDate the updDate to set
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * Method to return updAuthor 
	 * @return the updAuthor
	 */
	public String getUpdAuthor() {
		return updAuthor;
	}

	/**
	 * Method to set updAuthor
	 * @param updAuthor the updAuthor to set
	 */
	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	/**
	 * Method to return sign 
	 * @return the sign
	 */
	public BigDecimal getSign() {
		return sign;
	}

	/**
	 * Method to set sign
	 * @param sign the sign to set
	 */
	public void setSign(BigDecimal sign) {
		this.sign = sign;
	}
	
	/**
	 * Method check is entity fields are empty.
	 * @return true - if entity fields are empty.
	 */
	public boolean isEmpty() {
		boolean empty = true;
		empty &= id == null;
		empty &= StringUtils.isBlank(country);
		empty &= StringUtils.isBlank(state);
		empty &= StringUtils.isBlank(city);
		empty &= StringUtils.isBlank(region);
		empty &= StringUtils.isBlank(street);
		empty &= StringUtils.isBlank(number);
		empty &= StringUtils.isBlank(flat);
		empty &= StringUtils.isBlank(zip);
		return empty;
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
