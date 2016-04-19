package com.econtact.authWeb.app.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.church.ChurchEntity;
/**
 * Principal system object.
 * @author Yevgen Riaboshtan
 *
 */
public class EcontactPrincipal implements Principal, Serializable{
	private static final long serialVersionUID = -323490545353222491L;
	/**
	 * Logined user {@link SessionUserEntity}.
	 */
	private final SessionUserEntity userAccount;
	/**
	 * Available churchs {@link ChurchEntity} for work in.
	 */
	private final List<ChurchEntity> availableChurchs;
	/**
	 * Selected church {@link ChurchEntity} for current work.
	 */
	private ChurchEntity selectedChurch;
	/**
	 * If user work in SysAdmin mode.
	 */
	private boolean sysAdminMode;
	/**
	 * If user {@link SessionUserEntity} has {@link RoleType#ROLE_ADMIN} role and choose work as admin.
	 */
	private boolean adminMode;
	
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

	/**
	 * Method to return adminMode 
	 * @return the adminMode
	 */
	public boolean isAdminMode() {
		return adminMode;
	}

	/**
	 * Method to set adminMode
	 * @param adminMode the adminMode to set
	 */
	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

}
