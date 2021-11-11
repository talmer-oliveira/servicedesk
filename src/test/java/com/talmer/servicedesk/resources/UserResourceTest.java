package com.talmer.servicedesk.resources;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.dto.UserUpdateDTO;
import com.talmer.servicedesk.resources.exception.ResourceExceptionHandler;
import com.talmer.servicedesk.service.UserService;
import com.talmer.servicedesk.service.exception.ForbiddenException;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {

	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserResource userResource;
	
	private MockMvc mockMvc;
	
	private Gson gson;
	
	@BeforeEach
	void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ResourceExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        gson = new Gson();
    }
	
	@Test
	public void whenPOSTIsCalledThenAUserShouldBeRegistered() throws Exception {
		UserDTO expectedUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		User expectedSavedUser = new User("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		
		when(userService.createUser(any(UserDTO.class))).thenAnswer(invocationOnMock -> {
	        if (invocationOnMock.getArguments()[0] instanceof UserDTO) {
	            return expectedSavedUser;
	        }
	        return null;
	    });
		
		mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(expectedUserDTO)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}
	
	@Test
	public void whenPOSTIsCalledWithAnAlreadyRegisteredUserThenReturnAnError() throws Exception {
		UserDTO expectedUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		
		when(userService.createUser(any(UserDTO.class)))
		.thenThrow(new UserEmailAlreadyRegisteredException(expectedUserDTO.getEmail()));
		
		mockMvc.perform(post("/usuarios")
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(expectedUserDTO)))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.error", is("Forbidden")))
				.andExpect(jsonPath("$.message",
									is(String.format("O endereço de email %s já existe no sistema.",
													expectedUserDTO.getEmail()))));
	}

	@Test
	public void whenPUTIsCalledWithAValidRequestThenReturnAnNoContentSuccessStatus() throws Exception{
		String id = "617aff795aeb6e75aaf0dbb5";
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO("teste.person@mail.com", "Test Person Change", "01561607061");
		UserDTO expectedReturnedUserDTO = new UserDTO();
		expectedReturnedUserDTO.setId(id);
		expectedReturnedUserDTO.setName(userUpdateDTO.getName());
		expectedReturnedUserDTO.setEmail(userUpdateDTO.getEmail());
		expectedReturnedUserDTO.setCpf(userUpdateDTO.getCpf());

		when(userService.updateUser(eq(id), any(UserUpdateDTO.class))).thenAnswer(invocationOnMock -> {
	        if (invocationOnMock.getArguments()[0] instanceof UserUpdateDTO) {
	            return expectedReturnedUserDTO;
	        }
	        return null;
	    });

		mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/"+id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(userUpdateDTO)))
				.andExpect(status().isNoContent());
	}

	@Test
	public void whenPUTIsCalledWithATokenOwnedByAUserWithADifferentIdThenReturnAForbiddenErrorStatus() throws Exception{
		String id = "617aff795aeb6e75aaf0dbb5";
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO("teste.person@mail.com", "Test Person Change", "01561607061");
		String message = "Você não tem permissão para acessar esse recurso";

		when(userService.updateUser(eq(id), any(UserUpdateDTO.class)))
		.thenThrow(new ForbiddenException(message));

		mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/"+id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(userUpdateDTO)))
				.andExpect(status().isForbidden()).andExpect(jsonPath("$.error", is("Forbidden")))
				.andExpect(jsonPath("$.message", is(equalTo(message))));
	}

	
}
