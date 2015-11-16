package com.econtact.authWeb.app.helpers;

import java.io.File;

import javax.faces.bean.ManagedBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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

@ManagedBean(name = "menuHelper")
public class MenuHelper {
	
	private MenuModel topMenuModel;
	
	public MenuModel buildTopMenu() {
		if (topMenuModel == null) {
			topMenuModel = loadFromFile("topMenu.xml");
		}
		return topMenuModel;
	}

	
	private MenuModel loadFromFile(String fileName) {
		TopMenuBar topMenuBar;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TopMenuBar.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			topMenuBar = (TopMenuBar) jaxbUnmarshaller.unmarshal(new File(getClass().getClassLoader()
					.getResource(fileName).getFile()));
		} catch (JAXBException e) {
			topMenuBar = null;
		}
		if (topMenuBar == null) {
			topMenuBar = buildDefaultTopMenuBar();
		}
		return convertTopMenuModel(topMenuBar);
	}

	private TopMenuBar buildDefaultTopMenuBar() {
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
		
		TopMenuBar topMenuBar = new TopMenuBar();
		topMenuBar.getElements().add(usersSubMenu);
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
			item.setValue(LabelsHelper.getLocalizedMessage(element.getLabelLocaleKey()));
			item.setIcon(element.getIcon());
			item.setUrl(element.getPath());
			return item;
		} else if (element instanceof SubMenuTopMenuBar) {
			DefaultSubMenu item = new DefaultSubMenu();
			item.setId(element.getId());
			item.setLabel(LabelsHelper.getLocalizedMessage(element.getLabelLocaleKey()));
			item.setIcon(element.getIcon());
			for (MenuElementTopMenuBar elem : ((SubMenuTopMenuBar) element).getElements()) {
				item.getElements().add(convertMenuItem(elem));
			}
			return item;
		}
		return null;
	}
} 
