package com.econtact.authWeb.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.model.entity.accout.RoleType;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public class SuccessAuthenticatedHandler implements AuthenticationSuccessHandler {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SessionUserEntity user = (SessionUserEntity) authentication.getPrincipal();
		authenticationService.connectUser(request.getHeader("User-Agent"), request.getRemoteAddr(), user);
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_SUPER_ADMIN.getName()))) {
			response.sendRedirect(WebHelper.SUPER_ADMIN_PAGE);
		} else {
			response.sendRedirect(WebHelper.INDEX_PAGE);
		}
	}

}
