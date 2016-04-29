package com.econtact.dataModel.model.entity;

/**
 * Интерфейс для использования аудита сущностей
 * Interface for entity audit.
 * @author evgeniy
 *
 */
@FunctionalInterface
public interface AuditSupport {
	
	/**
	 * Возвращает сообщение о событии.
	 * @return - сообщение о событии.
	 */
	String getEnversNote();
}
