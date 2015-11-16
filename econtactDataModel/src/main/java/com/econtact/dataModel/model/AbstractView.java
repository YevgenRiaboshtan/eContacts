package com.econtact.dataModel.model;

import java.io.Serializable;

public interface AbstractView<PK extends Serializable> extends Serializable {
	PK getId();
}