package org.auth.dataModel.data.service;

import java.math.BigDecimal;
import java.util.List;

import org.auth.dataModel.data.context.UserContext;
import org.auth.dataModel.model.entity.dictionary.UniverDictEntity;

public interface UniverDictService {
	UniverDictEntity findById(BigDecimal id);

	List<UniverDictEntity> findUniverDictByParamDict(String paramDict);

	UniverDictEntity findByParamDictAndIdRecDict(String paramDict,
			Integer idRecDict);

	UniverDictEntity saveOrUpdate(UniverDictEntity entity, UserContext userContext);

	void remove(UniverDictEntity entity, UserContext userContext);
}
