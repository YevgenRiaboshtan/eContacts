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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.econtact.authWeb.app.helpers.WebHelper;
import com.econtact.dataModel.model.entity.accout.RoleType;

public class DefaultAdminFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getUserPrincipal() != null
				&& httpRequest.getUserPrincipal() instanceof UsernamePasswordAuthenticationToken) {
			for (GrantedAuthority item : ((UsernamePasswordAuthenticationToken) httpRequest.getUserPrincipal()).getAuthorities()) {
				if (RoleType.ROLE_SUPER_ADMIN.getName().equals(item.getAuthority())) {
					((HttpServletResponse)response).sendRedirect(httpRequest.getServletContext().getContextPath() + "/" + WebHelper.DEF_ADMIN_PAGE);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
