package com.econtact.authWeb.app.startup;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.InternalServerErrorException;

import com.econtact.authWeb.app.security.PasswordUtils;
import com.econtact.authWeb.app.utils.WebUtils;
import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

public class StartUpCheck implements Servlet{
	
	private String defaultAdminLogin;
	private String defaultAdminPassword;

	@Override
	public void init(ServletConfig config) throws ServletException {
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
		if (WebUtils.getBean(AuthenticationService.class).getUserByLogin(defaultAdminLogin) == null) {
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
				superAdmin = WebUtils.getBean(GenericService.class).saveOrUpdate(superAdmin, stubUserContext);
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
}
