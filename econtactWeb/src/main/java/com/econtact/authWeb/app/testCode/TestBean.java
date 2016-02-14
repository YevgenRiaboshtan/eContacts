package com.econtact.authWeb.app.testCode;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.econtact.authWeb.app.beans.view.UserSessionBean;
import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;

@ManagedBean
@ViewScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = -292856430946906320L;

	@Inject
	UserSessionBean userSessionBean;
	
	@EJB
	GenericService genService;
	
	private AccountUserEntity entity;
	private boolean editable;
	
	@PostConstruct
	public void init() {
		entity = genService.findById(AccountUserEntity.class, userSessionBean.getPrincipal().getId());
	}
	
	public void resetCurrent() {
	}

	public void testAll() {
	}

	public void test() throws Exception {
		System.out.println("test");
	}
	
	private Addres addr = new Addres();
	
	
	
	public Addres getAddr() {
		return addr;
	}

	public void setAddr(Addres addr) {
		this.addr = addr;
	}



	public AccountUserEntity getEntity() {
		return entity;
	}

	public void setEntity(AccountUserEntity entity) {
		this.entity = entity;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		System.out.println(editable + " -> " + this.editable);
		this.editable = editable;
	}



	public class Addres {
		private boolean editable;
		private String city;
		private String addres;
		private String zip;
		public boolean isEditable() {
			return editable;
		}
		public void setEditable(boolean editable) {
			this.editable = editable;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getAddres() {
			return addres;
		}
		public void setAddres(String addres) {
			this.addres = addres;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
		
	}
}
