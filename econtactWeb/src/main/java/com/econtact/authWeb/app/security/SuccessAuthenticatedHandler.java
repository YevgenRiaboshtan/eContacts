package com.econtact.authWeb.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.econtact.authWeb.app.beans.helper.NavigationHelper;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.model.entity.accout.RoleType;

public class SuccessAuthenticatedHandler implements AuthenticationSuccessHandler {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		EcontactPrincipal principal = (EcontactPrincipal) authentication.getPrincipal();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null) {  
     	   ipAddress = request.getRemoteAddr();  
        }
		authenticationService.connectUser(principal.getUserAccount(), request.getSession().getId(), ipAddress, request.getHeader("User-Agent"));
		if (principal.getUserAccount().getRole().equals(RoleType.ROLE_SUPER_ADMIN)) {
			principal.setSysAdminMode(true);
		} 
		response.sendRedirect(NavigationHelper.INDEX_PAGE);
	}
}
