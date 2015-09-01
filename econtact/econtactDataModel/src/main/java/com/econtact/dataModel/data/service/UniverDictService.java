package com.econtact.dataModel.data.service;

import java.math.BigDecimal;
import java.util.List;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

public interface UniverDictService {
	UniverDictEntity findById(BigDecimal id);

	List<UniverDictEntity> findUniverDictByParamDict(String paramDict);

	UniverDictEntity findByParamDictAndIdRecDict(String paramDict,
			Integer idRecDict);

	UniverDictEntity saveOrUpdate(UniverDictEntity entity, UserContext userContext);

	void remove(UniverDictEntity entity, UserContext userContext);
}
