package com.econtact.dataModel.data.service;

import java.math.BigDecimal;
import java.util.List;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

/**
 * CRUD and find operations with {@link UniverDictEntity} entity.
 * @author Yevgen Riaboshtan
 *
 */
public interface UniverDictService {
	/**
	 * Find {@link UniverDictEntity} by Id from hibernate cache.
	 * @param id - entity id.
	 * @return {@link UniverDictEntity} - entity
	 */
	UniverDictEntity findById(BigDecimal id);

	/**
	 * Find global (church is null) {@link UniverDictEntity} entities by paramDict 
	 * @param paramDict - paramDict value
	 * @return - {@link UniverDictEntity} entities collection.
	 */
	List<UniverDictEntity> findUniverDictByParamDict(String paramDict);
	
	/**
	 * Find {@link UniverDictEntity} entities by paramDict into a {@link ChurchEntity}.
	 * @param paramDict - paramDic value
	 * @param church - church
	 * @return - {@link UniverDictEntity} entities colleaction
	 */
	List<UniverDictEntity> findUniverDictByParamDict(String paramDict, ChurchEntity church);

	/**
	 * Find global (church is null) {@link UniverDictEntity} entity by paramDict and idRecDict
	 * @param paramDict - paramDict value
	 * @param idRecDict - idRecDict value
	 * @return - {@link UniverDictEntity} entity.
	 */
	//UniverDictEntity findByParamDictAndIdRecDict(String paramDict, Integer idRecDict);

	/**
	 * Find {@link UniverDictEntity} entity by paramDict and idRecDict in church
	 * @param paramDict - paramDict value
	 * @param idRecDict - idRecDict value
	 * @param church - church value
	 * @return - {@link UniverDictEntity} entity
	 */
	UniverDictEntity findByParamDictAndIdRecDict(String paramDict, Integer idRecDict, ChurchEntity church);
	
	/**
	 * Update or create {@link UniverDictEntity} entity
	 * @param entity - {@link UniverDictEntity} entity
	 * @param userContext - {@link UserContext} context of the user, that do change.
	 * @return - updated {@link UniverDictEntity} entity
	 */
	UniverDictEntity saveOrUpdate(UniverDictEntity entity, UserContext userContext);

	/**
	 * Remove {@link UniverDictEntity} entity
	 * @param entity - {@link UniverDictEntity} entity to remove
	 * @param userContext - {@link UserContext} context of the user, that do remove.
	 */
	void remove(UniverDictEntity entity, UserContext userContext);
}
