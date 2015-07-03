package app.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	
	public boolean isAccessForUser() {
		return isAccessForRole(RoleType.ROLE_USER);
	}
	
	public boolean isAccessForAdmin() {
		return isAccessForRole(RoleType.ROLE_ADMIN);
	}
	
	private boolean isAccessForRole(RoleType role) {
		if (!isAuth()
				|| SecurityContextHolder.getContext().getAuthentication().getAuthorities().isEmpty()) {
			return false;
		} 
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(role.toString()));
	}
}
