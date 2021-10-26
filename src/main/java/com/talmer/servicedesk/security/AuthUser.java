package com.talmer.servicedesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.talmer.servicedesk.domain.enums.Role;

public class AuthUser implements UserDetails{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String email;
	
	private String password;
	
	private Boolean active;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public AuthUser(String id, String email, String password, Boolean active, Set<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.active = active;
		authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r.getDescription())).collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
