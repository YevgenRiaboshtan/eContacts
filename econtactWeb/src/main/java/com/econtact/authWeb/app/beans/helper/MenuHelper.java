package com.econtact.authWeb.app.beans.helper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import com.econtact.authWeb.app.top.menu.MenuElementTopMenuBar;
import com.econtact.authWeb.app.top.menu.MenuItemTopMenuBar;
import com.econtact.authWeb.app.top.menu.SubMenuTopMenuBar;
import com.econtact.authWeb.app.top.menu.TopMenuBar;
import com.econtact.dataModel.data.util.LocaleLabels;
import com.econtact.dataModel.model.entity.accout.RoleType;

@Named
@ApplicationScoped
public class MenuHelper implements Serializable {
	private static final long serialVersionUID = 1915046053069380758L;

	public static final String SUPER_ADMIN_TOP_MENU_FILE_NAME = "superAdminTopMenu.xml";
	public static final String ADMIN_TOP_MENU_FILE_NAME = "adminTopMenu.xml";
	public static final String EMPLOYEE_TOP_MENU_FILE_NAME = "employeeTopMenu.xml";
	public static final String REGISTER_TOP_MENU_FILE_NAME = "registerTopMenu.xml";
	
	@Inject
	LabelsHelper labelsHelper;
	
	public MenuModel buildTopMenu(RoleType role) {
		String fileName = "";
		switch (role) {
		case ROLE_SUPER_ADMIN:
			fileName = SUPER_ADMIN_TOP_MENU_FILE_NAME;
			break;
		case ROLE_ADMIN:
			fileName = ADMIN_TOP_MENU_FILE_NAME;
			break;
		case ROLE_EMPLOYEE:
			fileName = EMPLOYEE_TOP_MENU_FILE_NAME;
			break;
		case ROLE_REGISTER:
			fileName = REGISTER_TOP_MENU_FILE_NAME;
			break;		
		default:
			break;
		}
		TopMenuBar topMenuBar = null;
		try {
			if (MenuHelper.class.getClassLoader().getResource(fileName) != null) {
				JAXBContext jaxbContext = JAXBContext.newInstance(TopMenuBar.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				topMenuBar = (TopMenuBar) jaxbUnmarshaller.unmarshal(
						new File(
							MenuHelper.class.getClassLoader().getResource(fileName).getFile()));
			}
		} catch (JAXBException e) {
			topMenuBar = null;
		}
		if (topMenuBar == null) {
			topMenuBar = buildDefaultTopMenuBar(role);
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(TopMenuBar.class);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			File outFile = new File(MenuHelper.class.getClassLoader().getResource(fileName).getFile());
			outFile.createNewFile();
			jaxbMarshaller.marshal(topMenuBar, outFile);
			} catch (JAXBException | IOException e) {
				e.printStackTrace();
			}
		}
		return convertTopMenuModel(topMenuBar);
	}

	private TopMenuBar buildDefaultTopMenuBar(RoleType role) {
		TopMenuBar topMenuBar = new TopMenuBar();
		switch (role) {
		case ROLE_SUPER_ADMIN:
			topMenuBar.getElements().addAll(createSuperAdminMenuItems());
			break;
		case ROLE_ADMIN:
			topMenuBar.getElements().addAll(createAdminMenuItems());
			break;
		case ROLE_EMPLOYEE:
			topMenuBar.getElements().addAll(createEmployeeMenuItems());
			break;
		default:
			break;
		}
		return topMenuBar;
	}
	
	private MenuModel convertTopMenuModel(TopMenuBar menuBar) {
		MenuModel menuModel = new DefaultMenuModel();
		for (MenuElementTopMenuBar element : menuBar.getElements()) {
			menuModel.addElement(convertMenuItem(element));
		}
		return menuModel;
	}
	
	private MenuElement convertMenuItem(MenuElementTopMenuBar element) {
		if (element instanceof MenuItemTopMenuBar) {
			DefaultMenuItem item = new DefaultMenuItem();
			item.setId(element.getId());
			item.setValue(labelsHelper.getLocalizedMessage(element.getLabelLocaleKey()));
			item.setIcon(element.getIcon());
			item.setUrl(element.getPath());
			return item;
		} else if (element instanceof SubMenuTopMenuBar) {
			DefaultSubMenu item = new DefaultSubMenu();
			item.setId(element.getId());
			item.setLabel(labelsHelper.getLocalizedMessage(element.getLabelLocaleKey()));
			item.setIcon(element.getIcon());
			for (MenuElementTopMenuBar elem : ((SubMenuTopMenuBar) element).getElements()) {
				item.getElements().add(convertMenuItem(elem));
			}
			return item;
		}
		return null;
	}
	
	private List<MenuElementTopMenuBar> createSuperAdminMenuItems() {
		List<MenuElementTopMenuBar> result = new ArrayList<>();
		
		SubMenuTopMenuBar usersSubMenu = new SubMenuTopMenuBar();
		usersSubMenu.setId("usersRootTopSubMenu");
		usersSubMenu.setIcon("fa fa-user");
		usersSubMenu.setLabelLocaleKey(LocaleLabels.TOP_MENU_USER_ITEM_SUB_MENU_LABEL);
		
		MenuItemTopMenuBar adminsListMenuItem = new MenuItemTopMenuBar();
		adminsListMenuItem.setId("usersListMenuItem");
		adminsListMenuItem.setPath("/superAdmin/adminsList.jsf");
		adminsListMenuItem.setLabelLocaleKey(LocaleLabels.TOP_MENU_USER_ITEM_SUB_MENU_ADMIN_LIST_MENU_ITEM_LABEL);
		usersSubMenu.getElements().add(adminsListMenuItem);
		
		MenuItemTopMenuBar connectLogMenuItem = new MenuItemTopMenuBar();
		connectLogMenuItem.setId("connectLogMenuItem");
		connectLogMenuItem.setPath("/superAdmin/connectLog.jsf");
		connectLogMenuItem.setLabelLocaleKey(LocaleLabels.TOP_MENU_USER_ITEM_SUB_MENU_CONNECT_LOGM_LIST_MENU_ITEM_LABEL);
		usersSubMenu.getElements().add(connectLogMenuItem);
		
		result.add(usersSubMenu);
		return result;
	}
	
	private List<MenuElementTopMenuBar> createAdminMenuItems() {
		List<MenuElementTopMenuBar> result = new ArrayList<>();
		return result;
	}
	
	private List<MenuElementTopMenuBar> createEmployeeMenuItems() {
		List<MenuElementTopMenuBar> result = new ArrayList<>();
		return result;
	}
} 
