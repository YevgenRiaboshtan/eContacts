package com.econtact.authWeb.app.beans.view.church;

import java.io.IOException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.econtact.authWeb.app.beans.view.GeneralCRUDBean;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.church.ChurchEntity;

@ManagedBean (name = "churchCRUDBean")
@ViewScoped
public class ChurchCRUDBean extends GeneralCRUDBean<ChurchEntity> {
	private static final long serialVersionUID = 5261936332118028517L;
	
	@Override
	protected boolean canModifyEntity(ChurchEntity entity) {
		if (EntityHelper.ACTUAL_SIGN.equals(entity.getSign())
				&& userSession.getPrincipal().equals(entity.getOwner())) {
			return true;
		}
		return false;
	}

	@Override
	protected ChurchEntity createDefaultEntity() {
		ChurchEntity entity = new ChurchEntity();
		entity.setOwner(userSession.getPrincipal());
		entity.setCreateDate(new Date());
		return entity;
	}
	
	protected ChurchEntity saveorUpdate(ChurchEntity entity, UserContext userContext) throws UniqueConstraintException {
		return genericService.saveOrUpdate(entity, userContext);
	}
	
	@Override
	protected void afterSaveNavigate() throws IOException {
		navigationHelper.navigate("/admin/church/list.jsf");
	}
	
	@Override
	protected void cancelNavigate() throws IOException {
		navigationHelper.navigate("/admin/church/list.jsf");
	}
}
