package com.econtact.authWeb.app.beans.view.print;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.export.PDFExporter;

@ManagedBean(name = "printBean")
@ViewScoped
public class PrintBean implements Serializable {
	private static final long serialVersionUID = -3658376433941523542L;

	public List<SelectItem> getDatatablePrintMenuItem(String targetDatatable) {
		List<SelectItem> result = new ArrayList<>();
		// <p:dataExporter type="xml" target="tbl" fileName="cars" pageOnly="true" />
		return result;
	}
}