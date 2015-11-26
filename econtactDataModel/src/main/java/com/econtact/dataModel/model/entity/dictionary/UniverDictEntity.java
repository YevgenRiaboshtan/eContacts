package com.econtact.dataModel.model.entity.dictionary;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AuditSupport;

@Entity
@Table(name = "univer_dict", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = UniverDictEntity.PARAM_DICT_ID_REC_DICT_SIGN_UNIQUE_CONSTRAINT, columnNames = {
		"sign", "param_dict", "id_rec_dict" })})
@NamedQuery(name = UniverDictEntity.FIND_ALL, query = "SELECT ude FROM UniverDictEntity ude WHERE ude.sign=:sign")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Audited
@AuditTable(value = "univer_dict_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
public class UniverDictEntity extends AbstractUniverDictEntity implements AuditSupport {
	private static final long serialVersionUID = 1L;
	private static final String NOTE_PATTERN = "Довідник ID: '%s', Назва довідника в універсальному довіднику: '%s'";
	
	@Override
	public String getEnversNote() {
		return String.format(NOTE_PATTERN, getId(), getNameRecDict());
	}
}
