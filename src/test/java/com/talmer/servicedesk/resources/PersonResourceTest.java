package com.talmer.servicedesk.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.gson.Gson;
import com.talmer.servicedesk.domain.Person;
import com.talmer.servicedesk.dto.PersonDTO;
import com.talmer.servicedesk.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonResourceTest {

	@Mock
	private PersonService personService;
	
	@InjectMocks
	private PersonResource personResource;
	
	private MockMvc mockMvc;
	
	private Gson gson;
	
	@BeforeEach
	void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        gson = new Gson();
    }
	
	@Test
	public void whenPOSTIsCalledThenAPersonShouldBeRegistered() throws Exception {
		PersonDTO expectedPersonDTO = new PersonDTO("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		Person expectedSavedPerson = new Person("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		
		Mockito.when(personService.insertPerson(Mockito.any(PersonDTO.class))).thenAnswer(invocationOnMock -> {
	        if (invocationOnMock.getArguments()[0] instanceof PersonDTO) {
	            return expectedSavedPerson;
	        }
	        return null;
	    });
		
		mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(expectedPersonDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.header().exists("Location"));
	}
}
