package com.econtact.authWeb.app.helpers;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean (name = "labelsHelper")
@ApplicationScoped
public class LabelsHelper {
	private static final ResourceBundle bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "viewMsg");
	
	public String getLocalizedMessage(String key) {
		if (key == null
				|| key.trim().isEmpty()) {
			return "";
		}
		return bundle.getString(key);
	}
	
	public String getLocalizedMessage(String key, Object ... args) {
		String bundleKey = getLocalizedMessage(key);
		return MessageFormat.format(bundleKey, args);
	}
}
