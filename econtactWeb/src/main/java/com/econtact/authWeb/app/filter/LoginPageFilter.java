package com.econtact.authWeb.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.econtact.authWeb.app.beans.helper.NavigationHelper;

public class LoginPageFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getUserPrincipal() != null) {
			((HttpServletResponse) response).sendRedirect(NavigationHelper.INDEX_PAGE);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {	
	}

}
