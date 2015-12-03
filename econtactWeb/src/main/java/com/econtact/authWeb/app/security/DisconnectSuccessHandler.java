package com.econtact.authWeb.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.data.service.AuthenticationService;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

public class DisconnectSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	AuthenticationService authenticationService;
	
	public DisconnectSuccessHandler() {
		super();
		setDefaultTargetUrl("/" + WebHelper.LOGIN_PAGE);
	} 
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication != null) {
			SessionUserEntity user = (SessionUserEntity) authentication.getPrincipal();
			authenticationService.disconnectUser(request.getRemoteAddr(), user);
		}
		super.onLogoutSuccess(request, response, authentication);
	}

}
