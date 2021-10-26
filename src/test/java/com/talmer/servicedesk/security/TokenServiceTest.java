package com.talmer.servicedesk.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenServiceTest {
    
    private String signingKey;
    private Long expiration;
    private String username = "email.test@email.com";

    @BeforeEach
    public void setUp(){
        signingKey = System.getenv("SERVICE_DESK_TOKEN_KEY");
        expiration = 86400L;
    }

    @Test
    public void whenCreateTokenIsCalledWithAUsernameAnExceptionShouldNotBeThrown(){
        TokenService tokenService = new TokenService(signingKey);

        Assertions.assertDoesNotThrow(() -> tokenService.createToken(username, expiration));
    }

}
