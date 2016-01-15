package com.econtact.dataModel.model.entity.dictionary;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;

@Entity
@Table(name = "univer_dict", schema = EntityHelper.E_CONTACT_SCHEMA)
public class UniverDictCheckEntity extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 6792528415203664950L;
	private static final String SEQ_NAME = "univerDictSeq";

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

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getParamDict() {
		return paramDict;
	}

	public void setParamDict(String paramDict) {
		this.paramDict = paramDict;
	}

	public Integer getIdRecDict() {
		return idRecDict;
	}

	public void setIdRecDict(Integer idRecDict) {
		this.idRecDict = idRecDict;
	}

	public String getAbrRecDict() {
		return abrRecDict;
	}

	public void setAbrRecDict(String abrRecDict) {
		this.abrRecDict = abrRecDict;
	}

	public String getNameRecDict() {
		return nameRecDict;
	}

	public void setNameRecDict(String nameRecDict) {
		this.nameRecDict = nameRecDict;
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
		setUpdAuthor("default system creation");
		setUpdDate(new Date());
	}
}
