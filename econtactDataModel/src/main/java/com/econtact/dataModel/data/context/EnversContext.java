package com.econtact.dataModel.data.context;

import java.io.Serializable;
/**
 * Envers context. Contained note of the event and event name.
 * 
 * Контекст аудирования операций содержащий название события и его описание.
 * 
 * @author Yevgen Riaboshtan
 *
 */
public class EnversContext implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Envers events note.
	 * 
	 * Описание события аудирования операции.
	 */
	private String note;
	/**
	 * Envers events name.
	 * 
	 * Название операции аудирования.
	 */
	private String nameEv;

	/**
	 * Create {@link EnversContext} instance with name and note.
	 * 
	 * Создает контекст аудирования операций {@link EnversContext} с указанным наименование и описанием.
	 * 
	 * @param nameEv - envers events name.
	 * 				- наименование операции аудирования.
	 * 
	 * @param note - events note.
	 * 				- описание события аудирования.
	 * 
	 * @return - created {@link EnversContext} instance for arguments.
	 * 			- созданный {@link EnversContext} контекст аудирования операций.
	 */
	public static EnversContext create(String nameEv, String note) {
		final EnversContext result = new EnversContext();
		result.nameEv = nameEv;
		result.note = note;
		return result;
	}

	/**
	 * Return note of the envers context.
	 * 
	 * Возвращает описание события из контекста.
	 * 
	 * @return - note of the envers context.
	 * 			- описание события из контекста.
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Return operations name of the envers context.
	 * 
	 * Возвращает наименование опреации.
	 * 
	 * @return - envers events name.
	 * 			- наименование операции.
	 */
	public String getNameEv() {
		return nameEv;
	}
}
