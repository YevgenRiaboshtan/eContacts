package com.econtact.dataModel.model.entity.dictionary;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.AuditSupport;
import com.econtact.dataModel.model.entity.ConstraintHelper;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@Entity
@Table(name = "univer_dict", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = ConstraintHelper.UNIVER_DICT_PARAM_ID_REC_DICT_SIGN_CHURCH, columnNames = {
		"sign", "param_dict", "id_rec_dict", "id_church_fk" }) })
@NamedQuery(name = UniverDictEntity.FIND_ALL, query = "SELECT ude FROM UniverDictEntity ude WHERE ude.sign=:sign")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Audited
@AuditTable(value = "univer_dict_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
@SQLDelete(sql = "UPDATE econtactschema.univer_dict set sign = id where id = ? and version = ?")
public class UniverDictEntity extends AbstractEntity<BigDecimal> implements AuditSupport {
	private static final long serialVersionUID = 1330174188604544322L;
	private static final String NOTE_PATTERN = "Довідник ID: '%s', Назва довідника в універсальному довіднику: '%s'";
	private static final String SEQ_NAME = "univerDictSeq";
	
	public static final String FIND_ALL = "UniverDictEntity.findAll";
	public static final String PARAM_DICT_A = "paramDict";
	public static final String CHURCH_A = "church";
	

	/**
	 * Идентификатор универсального справочника. Identifier of the Univerdict Entity.
	 */
	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_univer_dict_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = EntityHelper.ID_F, unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	/**
	 * Параметр довідника Parameter of the univerdict entity.
	 */
	@Column(name = "param_dict", nullable = false, length = 40)
	private String paramDict;

	/**
	 * Код запису довідника Code of the univerdict entity.
	 */
	@Column(name = "id_rec_dict", nullable = false, precision = 5)
	private Integer idRecDict;

	/**
	 * Абревіатура запису довідника Short label of the univerdict entity.
	 */
	@Column(name = "abr_rec_dict", nullable = true, length = 40)
	private String abrRecDict;

	/**
	 * Текст запису довідника Full name of the univerdict entity.
	 */
	@Column(name = "name_rec_dict", nullable = false, length = 200)
	private String nameRecDict;

	/**
	 * Автор последних изменений Last update author.
	 */
	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	/**
	 * Дата послдних изменений Last update date.
	 */
	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	/**
	 * Поле указывающее актуальность записи. sign = 0 - запись актуальная, sign = id - запись удалена. Field for mark
	 * row as deleted. If sign = 0 actual row, if sign = id - row is delete.
	 */
	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	@ManyToOne
	@JoinColumn(name = "id_church_fk", nullable = false)
	@Fetch(FetchMode.SELECT)
	private ChurchEntity church;

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
	 * Method to return paramDict 
	 * @return the paramDict
	 */
	public String getParamDict() {
		return paramDict;
	}

	/**
	 * Method to set paramDict
	 * @param paramDict the paramDict to set
	 */
	public void setParamDict(String paramDict) {
		this.paramDict = paramDict;
	}

	/**
	 * Method to return idRecDict 
	 * @return the idRecDict
	 */
	public Integer getIdRecDict() {
		return idRecDict;
	}

	/**
	 * Method to set idRecDict
	 * @param idRecDict the idRecDict to set
	 */
	public void setIdRecDict(Integer idRecDict) {
		this.idRecDict = idRecDict;
	}

	/**
	 * Method to return abrRecDict 
	 * @return the abrRecDict
	 */
	public String getAbrRecDict() {
		return abrRecDict;
	}

	/**
	 * Method to set abrRecDict
	 * @param abrRecDict the abrRecDict to set
	 */
	public void setAbrRecDict(String abrRecDict) {
		this.abrRecDict = abrRecDict;
	}

	/**
	 * Method to return nameRecDict 
	 * @return the nameRecDict
	 */
	public String getNameRecDict() {
		return nameRecDict;
	}

	/**
	 * Method to set nameRecDict
	 * @param nameRecDict the nameRecDict to set
	 */
	public void setNameRecDict(String nameRecDict) {
		this.nameRecDict = nameRecDict;
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
	 * Method to return church 
	 * @return the church
	 */
	public ChurchEntity getChurch() {
		return church;
	}

	/**
	 * Method to set church
	 * @param church the church to set
	 */
	public void setChurch(ChurchEntity church) {
		this.church = church;
	}

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdateData();
	}

	@PreUpdate
	public void setUpdateData() {
		setUpdDate(new Date());
		setUpdAuthor(EJBContext.get().getUser().getUpdData());
	}

	@Override
	public String getEnversNote() {
		return String.format(NOTE_PATTERN, getId(), getNameRecDict());
	}
}
