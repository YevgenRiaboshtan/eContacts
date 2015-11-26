package com.econtact.dataModel.model.entity.dictionary;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.econtact.dataModel.data.util.EntityHelper;

@Entity
@Table(name = "univer_dict", schema = EntityHelper.E_CONTACT_SCHEMA)
public class UniverDictCheckEntity extends AbstractUniverDictEntity {
	private static final long serialVersionUID = 6792528415203664950L;

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdAuthor("default system creation");
		setUpdDate(new Date());
	}
}
