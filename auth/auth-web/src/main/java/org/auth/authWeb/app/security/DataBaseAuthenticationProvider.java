package org.auth.authWeb.app.security;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.auth.dataModel.data.service.AuthenticationService;
import org.auth.dataModel.model.entity.accout.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class DataBaseAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private AuthenticationService authenticationService;

	public DataBaseAuthenticationProvider() {
		
	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {		
		String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserEntity user = authenticationService.getUserByNameOrEmail(login);
        if (user != null) {
        	return null;
        } else if (login.equals("user") && password.equals("user")) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(login, password, grantedAuths);
            return auth;
        } else if (login.equals("admin") && password.equals("admin")) {
        	List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            Authentication auth = new UsernamePasswordAuthenticationToken(login, password, grantedAuths);
            return auth;
        } else {
        	return null;
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
