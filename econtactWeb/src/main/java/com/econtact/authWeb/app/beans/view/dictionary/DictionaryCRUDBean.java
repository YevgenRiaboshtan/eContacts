package com.econtact.authWeb.app.beans.view.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.econtact.authWeb.app.beans.helper.LabelsHelper;
import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

@ManagedBean(name = "dictionaryCRUDBean")
@ViewScoped
public class DictionaryCRUDBean extends AbstractViewBean{
	private static final long serialVersionUID = -2350729694980777749L;

	@EJB
	UniverDictService univerDictService;
	
	@Inject
	LabelsHelper labelsHelper;
	
	private List<UniverDictEntity> univerDicts;
	private List<SelectItem> paramDicts;
	private String selectedParamDict;
	private String abrRecDict;
	private String nameRecDict;
	
	public List<SelectItem> getParamDicts() {
		if (paramDicts == null) {
			paramDicts = new ArrayList<SelectItem>();
			paramDicts.add(new SelectItem(null, labelsHelper.getLocalizedMessage(LocaleLabels.DICTIONARY_SELECT_PARAM_DICT), null, false, true, true));
			paramDicts.add(new SelectItem(NamesDictConstant.CONTACT_TYPE, labelsHelper.getLocalizedMessage(LocaleLabels.CONTACT_TYPE_UD_PARAM_DICT_LABEL)));
			paramDicts.add(new SelectItem(NamesDictConstant.PERSON_AGE_RANGE, labelsHelper.getLocalizedMessage(LocaleLabels.PERSON_AGE_RANGE_UD_PARAM_DICT_LABEL)));
			paramDicts.add(new SelectItem(NamesDictConstant.PERSON_STATUS, labelsHelper.getLocalizedMessage(LocaleLabels.PERSON_STATUS_UD_PARAM_DICT_LABEL)));
		}
		return paramDicts;
	}

	public List<UniverDictEntity> getUniverDicts() {
		if (univerDicts == null) {
			univerDicts = new ArrayList<UniverDictEntity>();
		}
		if (StringUtils.isBlank(selectedParamDict)) {
			univerDicts.clear();
		} else {
			univerDicts = univerDictService.findUniverDictByParamDict(selectedParamDict);
		}
		return univerDicts;
	}
	
	public void addUniverDict() {
		if (StringUtils.isNotBlank(selectedParamDict)
				&& StringUtils.isNotBlank(nameRecDict)) {
			UniverDictEntity entity = new UniverDictEntity();
			entity.setAbrRecDict(nameRecDict);
			entity.setNameRecDict(nameRecDict);
			entity.setParamDict(selectedParamDict);
			entity.setIdRecDict(univerDicts.isEmpty() ? 1 : univerDicts.stream().max((i1, i2) -> i1.getIdRecDict().compareTo(i2.getIdRecDict())).get().getIdRecDict() + 1);
			univerDictService.saveOrUpdate(entity, userSessionBean.getUserContext());
		} 
		abrRecDict = "";
		nameRecDict = "";
	}
	
	public void removeUniverDict(UniverDictEntity entity) {
		univerDictService.remove(entity, userSessionBean.getUserContext());
	}
	
	/**
	 * Method to return selectedParamDict 
	 * @return the selectedParamDict
	 */
	public String getSelectedParamDict() {
		return selectedParamDict;
	}

	/**
	 * Method to set selectedParamDict
	 * @param selectedParamDict the selectedParamDict to set
	 */
	public void setSelectedParamDict(String selectedParamDict) {
		this.selectedParamDict = selectedParamDict;
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
	
	
}
