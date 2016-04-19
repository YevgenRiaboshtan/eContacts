package com.econtact.authWeb.app.beans.view.security;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.authWeb.app.beans.helper.LabelsHelper;
import com.econtact.authWeb.app.beans.helper.NavigationHelper;
import com.econtact.authWeb.app.beans.view.AbstractViewBean;
import com.econtact.authWeb.app.security.EcontactPrincipal;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@ManagedBean(name = "selectChurchBean")
@ViewScoped
public class SelectChurchBean extends AbstractViewBean{
	private static final long serialVersionUID = 2537308684977779700L;

	@Inject
	NavigationHelper navigationHelper;
	
	@Inject
	LabelsHelper labelsHalper;
	
	private List<ChurchWrapper> availableChurch = new ArrayList<SelectChurchBean.ChurchWrapper>();
	
	@PostConstruct
	public void init() {
		EcontactPrincipal principal =(EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (RoleType.ROLE_ADMIN.equals(principal.getUserAccount().getRole())) {
			availableChurch.add(new ChurchWrapper(true, null));
		}
		principal.getAvailableChurchs().forEach(item -> availableChurch.add(new ChurchWrapper(false, item)));
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
       ChurchWrapper selectedItem = (ChurchWrapper) event.getObject();
       if (selectedItem != null) {
    	   ((EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setAdminMode(selectedItem.isAdminMode());
    	   ((EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setSelectedChurch(selectedItem.getChurch());
    	   navigationHelper.navigate(navigationHelper.getIndexPage());
       }
    }

	
	/**
	 * Method to return availableChurch 
	 * @return the availableChurch
	 */
	public List<ChurchWrapper> getAvailableChurch() {
		return availableChurch;
	}

	/**
	 * Method to set availableChurch
	 * @param availableChurch the availableChurch to set
	 */
	public void setAvailableChurch(List<ChurchWrapper> availableChurch) {
		this.availableChurch = availableChurch;
	}


	/**
	 * Item wrapper for DataTable.
	 * @author Yevgen Riaboshtan
	 *
	 */
	public class ChurchWrapper implements Serializable {
		private static final long serialVersionUID = -8426454234986453952L;
		/**
		 * Fake id for selection model in DataTable;
		 */
		private final BigDecimal id;
		/**
		 * if true - Item for select admin mode;
		 */
		private final boolean adminMode;
		/**
		 * Church {@link ChurchEntity} for select normal work in there. 
		 */
		private final ChurchEntity church;
		
		public ChurchWrapper(boolean adminMode, ChurchEntity church) {
			this.adminMode = adminMode;
			this.church = church;
			if (this.church == null) {
				this.id = BigDecimal.valueOf(-1);
			} else {
				this.id = this.church.getId();
			}
		}
		
		/**
		 * Method to return adminMode 
		 * @return the adminMode
		 */
		public boolean isAdminMode() {
			return adminMode;
		}
		/**
		 * Method to return church 
		 * @return the church
		 */
		public ChurchEntity getChurch() {
			return church;
		}
		
		/**
		 * Method to return id 
		 * @return the id
		 */
		public BigDecimal getId() {
			return id;
		}

		public String getText() {
			if (adminMode) {
				return labelsHalper.getLocalizedMessage(LocaleLabels.SELECT_ADMIN_MODE);
			} 
			if (church != null) {
				return labelsHalper.getLocalizedMessage(LocaleLabels.SELECT_NORMAL_MODE, church.getNameChurch(), church.getOwner().getLogin());
			}
			return "";
		}
	}
}
