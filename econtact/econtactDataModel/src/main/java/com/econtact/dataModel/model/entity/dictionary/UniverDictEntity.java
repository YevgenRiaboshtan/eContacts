package com.econtact.dataModel.model.entity.dictionary;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.AuditSupport;

@Entity
@Table(name = "univer_dict", schema = EntityHelper.E_CONTACT_SCHEMA)
@NamedQuery(name = UniverDictEntity.FIND_ALL, query = "SELECT ude FROM UniverDictEntity ude WHERE ude.sign=:sign")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Audited
@AuditTable(value = "univer_dict_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
public class UniverDictEntity extends AbstractEntity<BigDecimal> implements AuditSupport {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "UniverDictEntity.findAll";
	
	public static final String PARAM_DICT_A = "paramDict";
	
	private static final String SEQ_NAME = "univerDictSeq";
	private static final String NOTE_PATTERN = "Довідник ID: '%s', Назва довідника в універсальному довіднику: '%s'";

	@Id
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_univer_dict_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
	@Column(name = "id_univer_dict", unique = true, nullable = false, precision = 38, scale = 0)
	private BigDecimal id;

	@Column(name = "param_dict", nullable = false, length = 40)
	private String paramDict;

	@Column(name = "id_rec_dict", nullable = false, precision = 5)
	private Integer idRecDict;

	@Column(name = "abr_rec_dict", nullable = true, length = 40)
	private String abrRecDict;

	@Column(name = "name_rec_dict", nullable = false, length = 200)
	private String nameRecDict;

	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 120)
	private String updAuthor;

	@Override
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

	public BigDecimal getSign() {
		return sign;
	}

	public void setSign(BigDecimal sign) {
		this.sign = sign;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdAuthor() {
		return updAuthor;
	}

	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}
	
	@PrePersist
    public void prePersist() {
        sign = EntityHelper.ACTUAL_SIGN;
        setUpdateData();
    }

    @PreUpdate
    @PreRemove
    public void setUpdateData() {
        setUpdDate(new Date());
        setUpdAuthor(EJBContext.get().getUser().getUpdInfo());
    }

	@Override
	public String getEnversNote() {
		return String.format(NOTE_PATTERN, id, nameRecDict);
	}
}
