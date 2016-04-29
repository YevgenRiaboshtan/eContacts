package com.econtact.authWeb.app.testCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "testBean")
@ViewScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = -292856430946906320L;

	private String i1;
	private String i2;
	
	
	public void add() {
		
	}
	
	public void submit() {
		
	}
	
	/**
	 * Method to return i1 
	 * @return the i1
	 */
	public String getI1() {
		return i1;
	}

	/**
	 * Method to set i1
	 * @param i1 the i1 to set
	 */
	public void setI1(String i1) {
		this.i1 = i1;
	}

	/**
	 * Method to return i2 
	 * @return the i2
	 */
	public String getI2() {
		return i2;
	}

	/**
	 * Method to set i2
	 * @param i2 the i2 to set
	 */
	public void setI2(String i2) {
		this.i2 = i2;
	}

	
}
