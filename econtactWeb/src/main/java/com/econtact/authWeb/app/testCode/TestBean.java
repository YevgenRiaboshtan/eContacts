package com.econtact.authWeb.app.testCode;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	private void m(int a) {
		System.out.println("1" + (a + 1));
	}
	
	private void m(Integer a) {
		System.out.println("2" + (a + 2));
	}
	
	public void add() {
		
	}
	
	public void submit() {
		System.out.println(Thread.currentThread().getName() + "START");
		
		int max = Integer.MAX_VALUE / 1_00;
		ArrayList<String> arrayList = new ArrayList<String>(max);
		for (int i = 0; i < max; i++) {
			arrayList.add(new String("" + i));
		}
		System.out.println(arrayList.size() + " - " + Integer.MAX_VALUE);
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
