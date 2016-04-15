package com.econtact.authWeb.app.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

public class EcontactPrincipal implements Principal, Serializable{
	private static final long serialVersionUID = -323490545353222491L;

	private final SessionUserEntity userAccount;
	private final List<ChurchEntity> availableChurchs;
	private ChurchEntity selectedChurch;
	private boolean sysAdminMode;
	
	public EcontactPrincipal(@NotNull SessionUserEntity user, @NotNull List<ChurchEntity> availableChurchs) {
		this.userAccount = user;
		this.availableChurchs = availableChurchs;
	}
	
	@Override
	public String getName() {
		return userAccount.getLogin();
	}

	/**
	 * Method to return selectedChurch 
	 * @return the selectedChurch
	 */
	public ChurchEntity getSelectedChurch() {
		return selectedChurch;
	}

	/**
	 * Method to set selectedChurch
	 * @param selectedChurch the selectedChurch to set
	 */
	public void setSelectedChurch(ChurchEntity selectedChurch) {
		this.selectedChurch = selectedChurch;
	}

	/**
	 * Method to return userAccount 
	 * @return the userAccount
	 */
	public SessionUserEntity getUserAccount() {
		return userAccount;
	}

	/**
	 * Method to return availableChurchs 
	 * @return the availableChurchs
	 */
	public List<ChurchEntity> getAvailableChurchs() {
		return availableChurchs;
	}

	/**
	 * Method to return sysAdminMode 
	 * @return the sysAdminMode
	 */
	public boolean isSysAdminMode() {
		return sysAdminMode;
	}

	/**
	 * Method to set sysAdminMode
	 * @param sysAdminMode the sysAdminMode to set
	 */
	public void setSysAdminMode(boolean sysAdminMode) {
		this.sysAdminMode = sysAdminMode;
	}

}
