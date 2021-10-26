package com.talmer.servicedesk.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {
    
    @Value("${jwt.secret}")
    private String signingKey;

    public TokenService(String signingKey){
        this.signingKey = signingKey;
    }

    public String createToken(String username, Long secondsToExpire){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(secondsToExpire).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, signingKey.getBytes())
                .compact();
    }
}
