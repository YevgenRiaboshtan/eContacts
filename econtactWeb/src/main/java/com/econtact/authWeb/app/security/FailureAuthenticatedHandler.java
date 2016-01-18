package com.econtact.authWeb.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.econtact.authWeb.app.beans.helper.NavigationHelper;

public class FailureAuthenticatedHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof BadCredentialsException
				|| exception instanceof UsernameNotFoundException) {
			response.sendRedirect(request.getContextPath() + "/" + NavigationHelper.LOGIN_PAGE + "?error=authError");
		} else if (exception instanceof DisabledException) {
			response.sendRedirect(request.getContextPath() + "/" + NavigationHelper.LOGIN_PAGE + "?error=disabledUser");
		} else {
			response.sendRedirect(request.getContextPath() + "/" + NavigationHelper.LOGIN_PAGE + "?error=unknownError");
		}
	}

}
