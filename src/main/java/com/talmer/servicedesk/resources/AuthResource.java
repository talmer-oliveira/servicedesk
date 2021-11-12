package com.talmer.servicedesk.resources;

import javax.servlet.http.HttpServletResponse;

import com.talmer.servicedesk.security.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
    
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        String token = authService.refreshToken();
        response.addHeader("Authorization","Bearer "+token);
        return ResponseEntity.noContent().build();
    }
}
