package com.talmer.servicedesk.resources;

import static com.talmer.servicedesk.domain.enums.ServiceCategoryType.SERVICE_REQUEST;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.gson.Gson;
import com.talmer.servicedesk.domain.ServiceCategory;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.service.ServiceCategoryService;
import com.talmer.servicedesk.service.exception.ServiceCategoryAlreadyRegisteredException;

@ExtendWith(MockitoExtension.class)
public class ServiceCategoryResourceTest {

	@Mock
	private ServiceCategoryService categoryService;
	
	@InjectMocks
	private ServiceCategoryResource categoryResource;
	
	private MockMvc mockMvc;
	
	private Gson gson;
	
	@BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        gson = new Gson();
    }
	
	@Test
	public void whenPOSTIsCalledThenAServiceCategoryShouldBeCreated() throws Exception {
		ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO("Criação de Funcionalidade", SERVICE_REQUEST.getCode());
		ServiceCategory serviceCategory = new ServiceCategory("Criação de Funcionalidade", SERVICE_REQUEST);
		serviceCategory.setId("602d559c7c13bf5fd1894216");
		
		when(categoryService.createCategory(serviceCategoryDTO)).thenReturn(serviceCategory);
		mockMvc.perform(post("/categorias")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(serviceCategoryDTO)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}
	
	@Test
	public void whenPOSTIsCalledWithAlreadRegisteredCategoryThenAnErrorIsReturned() throws Exception {
		ServiceCategoryDTO alreadyRegisteredCategoryDTO = 
								new ServiceCategoryDTO("Criação de Funcionalidade", SERVICE_REQUEST.getCode());
		
		when(categoryService.createCategory(alreadyRegisteredCategoryDTO))
			.thenThrow(ServiceCategoryAlreadyRegisteredException.class);
		
		mockMvc.perform(post("/categorias")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(alreadyRegisteredCategoryDTO)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void whenGETListOfServiceCategoryIsCalledThenOKStatusIsReturned() throws Exception {
		List<ServiceCategoryDTO> expectedFoundList =  
				List.of(new ServiceCategoryDTO("Criação de Funcionalidade", SERVICE_REQUEST.getCode()),
					new ServiceCategoryDTO("Criação de Conta de Email", SERVICE_REQUEST.getCode()));
		
		when(categoryService.findAll()).thenReturn(expectedFoundList);
		
		mockMvc.perform(get("/categorias")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name", is("Criação de Funcionalidade")))
		.andExpect(jsonPath("$[0].categoryType", is(1)));
		
	}
}
