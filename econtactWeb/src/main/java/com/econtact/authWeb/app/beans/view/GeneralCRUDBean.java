package com.econtact.authWeb.app.beans.view;

import com.econtact.dataModel.model.entity.AbstractEntity;

/**
 * Abstract GeneralCRUD bean extends {@link AbstractCRUDBean} 
 * Bean define default cancel and after save navigation to the index page.
 * @author Yevgen Riaboshtan
 *
 * @param <T> - system entity extends {@link AbstractEntity}
 */
public abstract class GeneralCRUDBean<T extends AbstractEntity> extends AbstractCRUDBean<T> {
	private static final long serialVersionUID = 4070735677888224418L;

	@Override
	protected void afterSaveNavigate() {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
	
	@Override
	protected void cancelNavigate() {
		navigationHelper.navigate(navigationHelper.getIndexPage());
	}
}
