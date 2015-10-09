package com.econtact.authWeb.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.model.entity.accout.RoleType;

public class SuccessAuthenticatedHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ROLE_SUPER_ADMIN.getName()))) {
			response.sendRedirect(WebHelper.DEF_ADMIN_PAGE);
		} else {
			response.sendRedirect(WebHelper.INDEX_PAGE);
		}
	}

}
