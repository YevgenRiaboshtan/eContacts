package com.econtact.authWeb.app.beans.helper;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.data.util.LocaleLabels;

/**
 * Bean for get localized messages from other beans. 
 * @author Yevgen Riaboshtan
 *
 */
@Named
@ApplicationScoped
public class LabelsHelper {
	/**
	 * bundle localized message.
	 */
	private static final ResourceBundle bundle = FacesContext.getCurrentInstance().getApplication()
			.getResourceBundle(FacesContext.getCurrentInstance(), "viewMsg");
	
	/**
	 * Method return localized string by key.
	 * @param key - key
	 * @return string in current localization.
	 */
	public String getLocalizedMessage(String key) {
		if (key == null
				|| key.trim().isEmpty()) {
			return "";
		}
		return bundle.getString(key);
	}
	
	/**
	 * Method return localized string by key.
	 * @param key - key
	 * @param args - arguments
	 * @return string in localization.
	 */
	public String getLocalizedMessage(String key, Object ... args) {
		String bundleKey = getLocalizedMessage(key);
		return MessageFormat.format(bundleKey, args);
	}
	
	/**
	 * Method return localized labels for sign value.
	 * @param sign - sign value
	 * @return - localized string.
	 */
	public String getSignLabel(BigDecimal sign) {
		if (EntityHelper.ACTUAL_SIGN.equals(sign)) {
			return getLocalizedMessage(LocaleLabels.SIGN_FILTER_ACTUAL);
		} else {
			return getLocalizedMessage(LocaleLabels.SIGN_FILTER_DELETE);
		}
	}
}
