package com.econtact.dataModel.data.listeners;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.model.entity.common.AuditRevChangedEntity;
import com.econtact.dataModel.model.entity.common.AuditRevEntity;

public class AuditRevListener implements EntityTrackingRevisionListener {

	/**
     * Не проверяется @link EjbContext#get()#getEnversContext()} на NULL. NPE ошибка разработчика, если
     * сущность поддерживает аудит, должен быть установлен {@link com.econtact.dataModel.data.context.EnversContext}.
     *
     * @param revisionObj instance of revision entity.
     */
	@Override
	public void newRevision(Object revisionObj) {
		final AuditRevEntity revEntity = (AuditRevEntity) revisionObj;
		revEntity.setUser(EJBContext.get().getUser());
		revEntity.setDateEv(new Date());
		revEntity.setNameEv(EJBContext.get().getEnversContext().getNameEv());
		revEntity.setNote(EJBContext.get().getEnversContext().getNote());
	}

	@Override
	public void entityChanged(Class arg0, String entityName, Serializable entityId,
			RevisionType revisionType, Object revisionObj) {
		final AuditRevEntity revEntity = (AuditRevEntity) revisionObj;
		final AuditRevChangedEntity changedEntity = AuditRevChangedEntity.create(entityName, entityId);
		revEntity.addChangesEntity(changedEntity);		
	}

}
