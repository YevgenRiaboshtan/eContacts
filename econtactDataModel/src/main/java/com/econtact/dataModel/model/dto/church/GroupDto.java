package com.econtact.dataModel.model.dto.church;

import java.math.BigDecimal;

import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;

/**
 * Dto for groups list page.
 * @author evgeniy
 *
 */
public class GroupDto implements AbstractView<BigDecimal> {
	private static final long serialVersionUID = -4549827736059242243L;
	
	private BigDecimal id;
	
	private String name;
	
	private String description;
	
	private ConfirmStatusEnum viewPermit;
	
	private ConfirmStatusEnum editPermit;
	
	private String churchName;
	
	public GroupDto(BigDecimal id, String name, String description, ConfirmStatusEnum viewPermit,
			ConfirmStatusEnum editPermit, String churchName) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.viewPermit = viewPermit;
		this.editPermit = editPermit;
		this.churchName = churchName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ConfirmStatusEnum getViewPermit() {
		return viewPermit;
	}

	public void setViewPermit(ConfirmStatusEnum viewPermit) {
		this.viewPermit = viewPermit;
	}

	public ConfirmStatusEnum getEditPermit() {
		return editPermit;
	}

	public void setEditPermit(ConfirmStatusEnum editPermit) {
		this.editPermit = editPermit;
	}

	public String getChurchName() {
		return churchName;
	}

	public void setChurchName(String churchName) {
		this.churchName = churchName;
	}
}
