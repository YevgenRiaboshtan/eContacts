package com.econtact.authWeb.app.beans.view.dictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.CollectionDataModel;

import com.econtact.authWeb.app.beans.helper.LabelsHelper;
import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@ManagedBean(name = "dictionaryCRUDBean")
@ViewScoped
public class DictionaryCRUDBean extends AbstractViewBean {
	private static final long serialVersionUID = -2350729694980777749L;

	@EJB
	UniverDictService univerDictService;

	@Inject
	LabelsHelper labelsHelper;

	private CollectionDataModel<UniverDictEntity> univerDictModel;
	private List<SelectItem> paramDicts;
	private String selectedParamDict;
	private UniverDictEntity entity = new UniverDictEntity();

	public List<SelectItem> getParamDicts() {
		if (paramDicts == null) {
			paramDicts = new ArrayList<SelectItem>();
			paramDicts.add(new SelectItem(null, labelsHelper
					.getLocalizedMessage(LocaleLabels.DICTIONARY_SELECT_PARAM_DICT), null, false, true, true));
			paramDicts.add(new SelectItem(NamesDictConstant.CONTACT_TYPE, labelsHelper
					.getLocalizedMessage(LocaleLabels.CONTACT_TYPE_UD_PARAM_DICT_LABEL)));
			paramDicts.add(new SelectItem(NamesDictConstant.PERSON_AGE_RANGE, labelsHelper
					.getLocalizedMessage(LocaleLabels.PERSON_AGE_RANGE_UD_PARAM_DICT_LABEL)));
			paramDicts.add(new SelectItem(NamesDictConstant.PERSON_STATUS, labelsHelper
					.getLocalizedMessage(LocaleLabels.PERSON_STATUS_UD_PARAM_DICT_LABEL)));
			paramDicts.add(new SelectItem(NamesDictConstant.PERSON_SEX, labelsHelper
					.getLocalizedMessage(LocaleLabels.PERSON_SEX_UD_PARAM_DICT_LABEL)));
		}
		return paramDicts;
	}

	public CollectionDataModel<UniverDictEntity> getModel() {
		if (univerDictModel == null) {
			univerDictModel = new CollectionDataModel<UniverDictEntity>(
					StringUtils.isBlank(selectedParamDict) ? Collections.<UniverDictEntity> emptyList()
							: univerDictService.findUniverDictByParamDict(selectedParamDict));
		}
		return univerDictModel;
	}

	public void addUniverDict() {
		if (StringUtils.isNotBlank(selectedParamDict)) {
			entity.setParamDict(selectedParamDict);
			if (!((Collection<UniverDictEntity>) univerDictModel.getWrappedData()).isEmpty()) {
				if (((Collection<UniverDictEntity>) univerDictModel.getWrappedData()).stream().filter(item -> {return item.getChurch().equals(entity.getChurch());}).count() > 0) {
					entity.setIdRecDict(((Collection<UniverDictEntity>) univerDictModel.getWrappedData()).stream().filter(item -> {return item.getChurch().equals(entity.getChurch());})
							.max((i1, i2) -> i1.getIdRecDict().compareTo(i2.getIdRecDict())).get().getIdRecDict() + 1);
				} else {
					entity.setIdRecDict(1);
				}
			} else {
				entity.setIdRecDict(1);
			}		
			
			univerDictService.saveOrUpdate(entity, userSessionBean.getUserContext());
			univerDictModel = null;
			entity = new UniverDictEntity();
		}
	}

	public List<SelectItem> getChurchs() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		userSessionBean.getAvailableChurchs().stream().filter(item -> {
			return item.getOwner().equals(userSessionBean.getPrincipal());
		}).forEach(item -> result.add(new SelectItem(item, item.getNameChurch())));
		return result;
	}

	public void removeUniverDict(UniverDictEntity entity) {
		univerDictService.remove(entity, userSessionBean.getUserContext());
		univerDictModel = null;
	}

	public void onRowEdit(RowEditEvent event) {
		univerDictService.saveOrUpdate((UniverDictEntity) event.getObject(), userSessionBean.getUserContext());
		univerDictModel = null;
	}

	/**
	 * Method to return selectedParamDict
	 * 
	 * @return the selectedParamDict
	 */
	public String getSelectedParamDict() {
		return selectedParamDict;
	}

	/**
	 * Method to set selectedParamDict
	 * 
	 * @param selectedParamDict
	 *            the selectedParamDict to set
	 */
	public void setSelectedParamDict(String selectedParamDict) {
		this.selectedParamDict = selectedParamDict;
		univerDictModel = null;
	}

	/**
	 * Method to return entity
	 * 
	 * @return the entity
	 */
	public UniverDictEntity getEntity() {
		return entity;
	}

	/**
	 * Method to set entity
	 * 
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(UniverDictEntity entity) {
		this.entity = entity;
	}
}
