package com.econtact.authWeb.app.security;

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
import com.econtact.dataModel.model.entity.accout.ConfirmStatusEnum;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;
import com.econtact.dataModel.model.entity.accout.UserStatusEnum;

public class DataBaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private GenericService genericService;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String login = authentication.getName();
		String password = authentication.getCredentials().toString();
		AccountUserEntity accountUser = authenticationService.getUserByLogin(login);
		if (accountUser != null) {
			if (PasswordUtils.machPassword(accountUser.getPassword(), password, accountUser.getSalt())) {
				SessionUserEntity sessionUser = genericService.findById(SessionUserEntity.class, accountUser.getId());
				List<GrantedAuthority> grants = new ArrayList<>();
				if (UserStatusEnum.ENABLE.equals(accountUser.getEnabledUser())
						&& ConfirmStatusEnum.CONFIRMED.equals(accountUser.getRoleConfirm())) {
							grants.add(new SimpleGrantedAuthority(accountUser.getRole().getName()));
						
						return new UsernamePasswordAuthenticationToken(new EcontactPrincipal(sessionUser, authenticationService.getAvailableChurchs(sessionUser)), password, grants);
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
