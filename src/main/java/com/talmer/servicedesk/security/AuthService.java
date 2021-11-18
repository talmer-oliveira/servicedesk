package com.talmer.servicedesk.security;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    
    @Autowired
    private TokenService tokenService;

    public AuthUser authenticated() {
		try {
			return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

    public String refreshToken(){
        AuthUser authUser = authenticated();
        return tokenService.createToken(authUser.getUsername(), Duration.ofSeconds(60L));
    }
}
