package com.econtact.authWeb.app.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.econtact.authWeb.app.security.EcontactPrincipal;
import com.econtact.dataModel.data.service.AuthenticationService;

public class ChurchFilterChain implements Filter {

	@EJB
	AuthenticationService authenticationService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		//FIXME fix resource not found
		if (!((HttpServletRequest) request).getRequestURI().startsWith("/econtact/javax.faces.resource")
				&& ((HttpServletRequest) request).getUserPrincipal() != null
				&& ((HttpServletRequest) request).getUserPrincipal() instanceof Authentication) {
			EcontactPrincipal principal = (EcontactPrincipal) ((Authentication) ((HttpServletRequest) request).getUserPrincipal()).getPrincipal();
			if (principal.isSysAdminMode()
					|| principal.isAdminMode()
					|| principal.getSelectedChurch() != null) {
				chain.doFilter(request, response);	
			} else if (principal.getSelectedChurch() == null) {
				if (principal.getAvailableChurchs().isEmpty()) {
					authenticationService.disconnectUser(((HttpServletRequest) request).getSession().getId());
					((HttpServletRequest) request).getSession().invalidate();
			    	((HttpServletRequest) request).logout();
			    	((HttpServletResponse) response).sendRedirect("emptyChurch.xhtml");
				} else {
					request.getRequestDispatcher("selectChurch.xhtml").forward(request, response);
				}
			} 
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
