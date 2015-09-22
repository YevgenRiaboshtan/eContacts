package com.econtact.authWeb.app.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserEntity;

public class DataBaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AuthenticationService authenticationService;
	private final String defaultAdminLogin;
	private final String defaultAdminPassword;

	{
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
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String login = authentication.getName();
		String password = authentication.getCredentials().toString();
		/* Check is defaultAdmin*/
		if (defaultAdminLogin.equals(login)
				&& defaultAdminPassword.equals(password)) {
			List<GrantedAuthority> grants = new ArrayList<>();
			grants.add(new SimpleGrantedAuthority(RoleType.ROLE_DEF_ADMIN.getName()));
			UserEntity defaultAdmin = new UserEntity();
			defaultAdmin.setFirstName(defaultAdminLogin);
			defaultAdmin.setLastName(defaultAdminLogin);
			defaultAdmin.setLogin(defaultAdminLogin);
			defaultAdmin.addRole(RoleType.ROLE_DEF_ADMIN);
			return new UsernamePasswordAuthenticationToken(defaultAdmin, password, grants);
		} 
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
