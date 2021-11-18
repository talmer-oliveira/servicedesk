package com.talmer.servicedesk.security;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.talmer.servicedesk.security.exception.InvalidTokenException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenService {
    
    @Value("${jwt.secret}")
    private String signingKey;

    public TokenService(String signingKey){
        this.signingKey = signingKey;
    }

    public TokenService(){}

    public String createToken(String username, Duration expiration){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(LocalDateTime.now().plus(expiration).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, signingKey.getBytes())
                .compact();
    }

    public String getUsername(String token) throws InvalidTokenException{
        Claims claims = fromToken(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            fromToken(token);
            return true;
        } catch (InvalidTokenException e) {
            return false;
        }
    }

    private Claims fromToken(String token) throws InvalidTokenException{
        try {
            return Jwts.parser().setSigningKey(signingKey.getBytes()).parseClaimsJws(token).getBody();
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | ExpiredJwtException e) {
            throw new InvalidTokenException(e.getMessage());
        }
        
    }
}
