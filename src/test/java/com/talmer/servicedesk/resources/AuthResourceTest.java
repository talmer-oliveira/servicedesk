package com.talmer.servicedesk.resources;

import com.talmer.servicedesk.security.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthResourceTest {
    
    @Mock
    private AuthService authService;

    @InjectMocks
    AuthResource authResource;

    private MockMvc mockMvc;

    @BeforeEach
	void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void whenRefreshTokenIsCalledWithAnStillValidTokenThenReturnANewToken() throws Exception{
        String token = "eyJhbGciOiJIUzUxMiJ9";
        Mockito.when(authService.refreshToken()).thenReturn(token);

        mockMvc.perform(post("/auth/refreshToken"))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Authorization", "Bearer " + token));
    }
}
