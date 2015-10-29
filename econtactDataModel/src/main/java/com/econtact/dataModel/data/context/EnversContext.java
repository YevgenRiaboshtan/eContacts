package com.econtact.dataModel.data.context;

import java.io.Serializable;

import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

public class EnversContext implements Serializable {
	private static final long serialVersionUID = 1L;

	private String note;
	private UniverDictEntity event;
	private String nameEv;

	public static EnversContext create(UniverDictEntity event, String nameEv,
			String note) {
		final EnversContext result = new EnversContext();
		result.event = event;
		result.nameEv = nameEv;
		result.note = note;
		return result;
	}

	public String getNote() {
		return note;
	}

	public UniverDictEntity getEvent() {
		return event;
	}

	public String getNameEv() {
		return nameEv;
	}
}
