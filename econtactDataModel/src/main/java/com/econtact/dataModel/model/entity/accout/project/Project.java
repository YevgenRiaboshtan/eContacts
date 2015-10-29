package com.econtact.dataModel.model.entity.accout.project;

import java.math.BigDecimal;
import java.util.Date;

import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public class Project extends AbstractEntity<BigDecimal> {
	private static final long serialVersionUID = 6692176964337384451L;

	private BigDecimal id;
	
	private String nameProject;
	
	private String description;
	
	private SessionUserEntity owner;
	
	private Date updDate;
	
	private String updAuthor;
	
	private BigDecimal sign;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SessionUserEntity getOwner() {
		return owner;
	}

	public void setOwner(SessionUserEntity owner) {
		this.owner = owner;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdAuthor() {
		return updAuthor;
	}

	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	public BigDecimal getSign() {
		return sign;
	}

	public void setSign(BigDecimal sign) {
		this.sign = sign;
	}
	
}
