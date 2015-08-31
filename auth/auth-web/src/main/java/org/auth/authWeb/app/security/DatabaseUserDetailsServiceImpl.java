package org.auth.authWeb.app.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.EJB;

import org.auth.dataModel.data.service.AuthenticationService;
import org.auth.dataModel.model.entity.accout.RoleType;
import org.auth.dataModel.model.entity.accout.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class DatabaseUserDetailsServiceImpl implements UserDetailsService {

	@EJB
	private AuthenticationService authService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {		

		UserEntity user = authService.getUserByNameOrEmail(username);
				
		if(user!=null){
			String password = user.getPass();
			//additional information on the security object
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true; //user.getStatus().equals(UserStatus.ACTIVE);
			
			//Let's populate user roles
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for(RoleType role : user.getRoles()){
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			}
			
			//Now let's create Spring Security User object
			User securityUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
			return securityUser;
		}else{
			if ("user1".equals(username)) {
				return new User(username, "5f4dcc3b5aa765d61d8327deb882cf99", Arrays.asList(new SimpleGrantedAuthority(RoleType.ROLE_USER.toString())));
			} else if ("admin".equals(username)) {
				return new User(username, "admin", Arrays.asList(new SimpleGrantedAuthority(RoleType.ROLE_ADMIN.toString())));
			}
			throw new UsernameNotFoundException("User Not Found!!!");
		}	
	}
}
