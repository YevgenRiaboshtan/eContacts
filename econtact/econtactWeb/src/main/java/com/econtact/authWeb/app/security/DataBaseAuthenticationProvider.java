package com.econtact.authWeb.app.security;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.data.service.GenericService;
import com.econtact.dataModel.model.entity.accout.AccountUserEntity;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

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
		/* Check is defaultSuperAdmin*/
		if (defaultAdminLogin.equals(login)
				&& defaultAdminPassword.equals(password)) {
			List<GrantedAuthority> grants = new ArrayList<>();
			grants.add(new SimpleGrantedAuthority(RoleType.ROLE_SUPER_ADMIN.getName()));
			SessionUserEntity defaultAdmin = new SessionUserEntity();
			defaultAdmin.setId(new BigDecimal(-1));
			defaultAdmin.setFirstName(defaultAdminLogin);
			defaultAdmin.setLastName(defaultAdminLogin);
			defaultAdmin.setLogin(defaultAdminLogin);
			return new UsernamePasswordAuthenticationToken(defaultAdmin, password, grants);
		} 
		AccountUserEntity user = authenticationService.getUserByLogin(login);
		if (user != null) {
			if (PasswordUtils.machPassword(user.getPassword(), password, user.getSalt())) {
				SessionUserEntity result = genericService.findById(SessionUserEntity.class, user.getId());
				List<GrantedAuthority> grants = new ArrayList<>();
				if (UserStatusEnum.ENABLE.equals(user.getEnabledUser())
						&& ConfirmStatusEnum.CONFIRMED.equals(user.getRoleConfirm())) {
							grants.add(new SimpleGrantedAuthority(user.getRole().getName()));
					return new UsernamePasswordAuthenticationToken(result, password, grants);
				} else {
					throw new DisabledException("user is disabled.");
				}
			} else {
				throw new BadCredentialsException("password is incorrect !");
			}	
		} else {
			throw new UsernameNotFoundException("userNotFound");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
