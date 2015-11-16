package com.econtact.authWeb.app.top.menu;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "menuElement")
public class MenuElementTopMenuBar implements Serializable {
	private static final long serialVersionUID = 2404880864974386815L;

	@XmlElement
	private String id;

	@XmlElement
	private String labelLocaleKey;

	@XmlElement
	private String icon;

	@XmlElement
	private String path;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelLocaleKey() {
		return labelLocaleKey;
	}

	public void setLabelLocaleKey(String label) {
		this.labelLocaleKey = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
