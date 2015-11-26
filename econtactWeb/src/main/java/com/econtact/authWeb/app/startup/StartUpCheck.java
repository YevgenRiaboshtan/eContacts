package com.econtact.authWeb.app.startup;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.InternalServerErrorException;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.service.UniverDictService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;
import com.econtact.dataModel.model.entity.dictionary.DictionaryConstant;
import com.econtact.dataModel.model.entity.dictionary.NamesDictConstant;
import com.econtact.dataModel.model.entity.dictionary.UniverDictCheckEntity;
import com.econtact.dataModel.model.entity.dictionary.UniverDictEntity;

public class StartUpCheck implements Servlet{
	
	private String defaultAdminLogin;
	private String defaultAdminPassword;

	@Override
	public void init(ServletConfig config) throws ServletException {
		final UniverDictService univerDictService = WebHelper.getBean(UniverDictService.class);
		checkActionUniverDicts(univerDictService);
		checkEventUniverDicts(univerDictService);
		if (System.getProperty("com.econtact.defaultAdminLogin") != null) {
			defaultAdminLogin = System
					.getProperty("com.econtact.defaultAdminLogin");
		} else {
			defaultAdminLogin = "admin";
		}
		if (System.getProperty("com.econtact.defaultAdminPassword") != null) {
			defaultAdminPassword = System
					.getProperty("com.econtact.defaultAdminPassword");
		} else {
			defaultAdminPassword = "admin";
		}
		if (WebHelper.getBean(AuthenticationService.class).getUserByLogin(defaultAdminLogin) == null) {
			AccountUserEntity superAdmin = new AccountUserEntity();
			superAdmin.setLogin(defaultAdminLogin);
			superAdmin.setFirstName(defaultAdminLogin);
			superAdmin.setLastName(defaultAdminLogin);
			superAdmin.setSalt(PasswordUtils.getRandomSalt());
			superAdmin.setPassword(PasswordUtils.convertPassword(defaultAdminPassword, superAdmin.getSalt()));
			superAdmin.setRole(RoleType.ROLE_SUPER_ADMIN);
			superAdmin.setRoleConfirm(ConfirmStatusEnum.CONFIRMED);
			superAdmin.setEnabledUser(UserStatusEnum.ENABLE);
			superAdmin.setAllowCreateRegister(false);
			try {
				final UserContext stubUserContext = UserContext.create(null, TimeZone.getTimeZone("GMT+2"));
				superAdmin = WebHelper.getBean(GenericService.class).saveOrUpdate(superAdmin, stubUserContext);
			} catch (UniqueConstraintException e) {
				throw new InternalServerErrorException("Creating default admin error");
			}
		}
	}
	
	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
	}

	
	private void checkActionUniverDicts(final UniverDictService univerDictService) {
		List<UniverDictEntity> list = univerDictService.findUniverDictByParamDict(NamesDictConstant.ACTION);
		UniverDictEntity connectItem = null;
		UniverDictEntity disconnectItem = null;
	
		for (UniverDictEntity item : list) {
			switch (item.getIdRecDict()) {
			case DictionaryConstant.ACTION_CONNECT:	
				connectItem = item;
				break;
			case DictionaryConstant.ACTION_DISCONNECT:
				disconnectItem = item;
				break;
			default:
				break;
			}
		}
		
		if (connectItem == null) {
			UniverDictCheckEntity connect = new UniverDictCheckEntity();
			connect.setAbrRecDict("подкл.");
			connect.setIdRecDict(DictionaryConstant.ACTION_CONNECT);
			connect.setNameRecDict("Подключение");
			connect.setParamDict(NamesDictConstant.ACTION);
			WebHelper.getBean(UniverDictService.class).saveOrUpdate(connect);
		}
		
		if (disconnectItem == null) {
			UniverDictCheckEntity disconnect = new UniverDictCheckEntity();
			disconnect.setAbrRecDict("откл.");
			disconnect.setIdRecDict(DictionaryConstant.ACTION_DISCONNECT);
			disconnect.setNameRecDict("Отключение");
			disconnect.setParamDict(NamesDictConstant.ACTION);
			WebHelper.getBean(UniverDictService.class).saveOrUpdate(disconnect);
		}		
	}
	
	private void checkEventUniverDicts(final UniverDictService univerDictService) {
		List<UniverDictEntity> list = univerDictService.findUniverDictByParamDict(NamesDictConstant.EVENT);
		UniverDictEntity createItem = null;
		UniverDictEntity updateItem = null;
		UniverDictEntity removeItem = null;
		
		for (UniverDictEntity item : list) {
			switch (item.getIdRecDict()) {
			case DictionaryConstant.EVENT_CREATE:
				createItem = item;
				break;
			case DictionaryConstant.EVENT_REMOVE:
				removeItem = item;
				break;
			case DictionaryConstant.EVENT_UPDATE:
				updateItem = item;
				break;
			default:
				break;
			}
		}
		
		if (createItem == null) {
			UniverDictCheckEntity create = new UniverDictCheckEntity();
			create.setAbrRecDict("создание");
			create.setIdRecDict(DictionaryConstant.EVENT_CREATE);
			create.setNameRecDict("Создание");
			create.setParamDict(NamesDictConstant.EVENT);
			WebHelper.getBean(UniverDictService.class).saveOrUpdate(create);
		}
		if (updateItem == null) {
			UniverDictCheckEntity update = new UniverDictCheckEntity();
			update.setAbrRecDict("обновление");
			update.setIdRecDict(DictionaryConstant.EVENT_UPDATE);
			update.setNameRecDict("Обновление");
			update.setParamDict(NamesDictConstant.EVENT);
			WebHelper.getBean(UniverDictService.class).saveOrUpdate(update);
		}
		if (removeItem == null) {
			UniverDictCheckEntity remove = new UniverDictCheckEntity();
			remove.setAbrRecDict("удаление");
			remove.setIdRecDict(DictionaryConstant.EVENT_REMOVE);
			remove.setNameRecDict("Удаление");
			remove.setParamDict(NamesDictConstant.EVENT);
			WebHelper.getBean(UniverDictService.class).saveOrUpdate(remove);
		}	
	}	
}
