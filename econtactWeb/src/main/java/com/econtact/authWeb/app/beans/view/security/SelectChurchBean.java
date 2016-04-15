package com.econtact.authWeb.app.beans.view.security;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;

import com.econtact.authWeb.app.beans.helper.NavigationHelper;
import com.econtact.authWeb.app.security.EcontactPrincipal;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@ManagedBean(name = "selectChurchBean")
@ViewScoped
public class SelectChurchBean {

	@Inject
	NavigationHelper navigationHelper;
	
	public void selectChurch() throws IOException {
		List<ChurchEntity> churchs = ((EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAvailableChurchs();
		if (!churchs.isEmpty()) {
			((EcontactPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setSelectedChurch(churchs.get(0));
			System.out.println("select some");
		}
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
}
