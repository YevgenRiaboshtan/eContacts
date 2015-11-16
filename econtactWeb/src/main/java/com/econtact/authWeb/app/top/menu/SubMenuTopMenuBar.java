package com.econtact.authWeb.app.top.menu;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="subMenuItem")
public class SubMenuTopMenuBar  extends MenuElementTopMenuBar {
	private static final long serialVersionUID = -5418162341013814724L;

	@XmlElementWrapper
	@XmlElementRef
	LinkedHashSet<MenuElementTopMenuBar> elements = new LinkedHashSet<>();

	public LinkedHashSet<MenuElementTopMenuBar> getElements() {
		return elements;
	}

	public void setElements(LinkedHashSet<MenuElementTopMenuBar> elements) {
		this.elements = elements;
	}
	
}
