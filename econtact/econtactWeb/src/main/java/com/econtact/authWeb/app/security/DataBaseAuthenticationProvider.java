package com.econtact.authWeb.app.security;

import java.math.BigDecimal;
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
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.AccessStatusEnum;
import com.econtact.dataModel.model.entity.accout.AdvanceUserEntity;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.UserEntity;
import com.econtact.dataModel.model.entity.accout.UserRoleRel;

public class DataBaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private GenericService genericService;
	
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
			grants.add(new SimpleGrantedAuthority(RoleType.ROLE_SUPER_ADMIN.getName()));
			UserEntity defaultAdmin = new UserEntity();
			defaultAdmin.setId(new BigDecimal(-1));
			defaultAdmin.setFirstName(defaultAdminLogin);
			defaultAdmin.setLastName(defaultAdminLogin);
			defaultAdmin.setLogin(defaultAdminLogin);
			return new UsernamePasswordAuthenticationToken(defaultAdmin, password, grants);
		} 
		AdvanceUserEntity user = authenticationService.findUser(login);
		if (user != null) {
			UserEntity result = genericService.findById(UserEntity.class, user.getId());
			List<GrantedAuthority> grants = new ArrayList<>();
			for (UserRoleRel item : user.getRoles()) {
				if (AccessStatusEnum.CONFIRMED.equals(item.getConfirm())) {
					grants.add(new SimpleGrantedAuthority(item.getRole().getName()));
				}
			}
			return new UsernamePasswordAuthenticationToken(result, password, grants);
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
