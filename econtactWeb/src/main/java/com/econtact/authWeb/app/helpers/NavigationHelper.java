package com.econtact.authWeb.app.helpers;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="navigationHelper")
@RequestScoped
public class NavigationHelper {
	
	public static final String LIST = "list.jsf";
	public static final String NEW = "new.jsf";
	public static final String EDIT = "edit.jsf";
	public static final String ID_PARAM = "id";
	
	public void navigate(String page) throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect(page);
	}
	
	public String getListPage(){
		String res = LIST;
		return res;
	}
	
	public String getNewPage(){
		String res = NEW;
		return res;
	}
	
	public String getEditPage(String id){
		String res = EDIT+"?"+ID_PARAM+"="+id;
		return res;
	}
}
