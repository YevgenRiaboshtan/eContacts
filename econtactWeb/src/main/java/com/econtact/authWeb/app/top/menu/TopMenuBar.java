package com.econtact.authWeb.app.top.menu;

import java.io.Serializable;
import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TopMenuBar implements Serializable {
	private static final long serialVersionUID = -756553213037974024L;

	@XmlElementWrapper
	@XmlElementRef
	private LinkedHashSet<MenuElementTopMenuBar> elements = new LinkedHashSet<MenuElementTopMenuBar>();

	public LinkedHashSet<MenuElementTopMenuBar> getElements() {
		return elements;
	}

	public void setElements(LinkedHashSet<MenuElementTopMenuBar> elements) {
		this.elements = elements;
	}
}
