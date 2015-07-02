package app.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean
public class WebHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean isAuth() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}
	
	public String getUserName() {
		return isAuth() ? SecurityContextHolder.getContext().getAuthentication().getName() : "Empty UserName";
	}
}
