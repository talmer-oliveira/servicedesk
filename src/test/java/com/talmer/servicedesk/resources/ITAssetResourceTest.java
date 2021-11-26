package com.talmer.servicedesk.resources;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.talmer.servicedesk.domain.ITAsset;
import com.talmer.servicedesk.domain.enums.AssetType;
import com.talmer.servicedesk.dto.ITAssetDTO;
import com.talmer.servicedesk.service.ITAssetService;
import com.talmer.servicedesk.service.exception.ITAssetAlreadyRegisteredException;

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

@ExtendWith(MockitoExtension.class)
public class ITAssetResourceTest {
    
    @Mock
	private ITAssetService assetService;
	
	@InjectMocks
	private ITAssetResource assetResource;
	
	private MockMvc mockMvc;
	
	private Gson gson;
	
	@BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(assetResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        gson = new Gson();
    }

    @Test
	public void whenPOSTIsCalledThenAServiceCategoryShouldBeCreated() throws Exception {
		ITAssetDTO expectAssetDTO = new ITAssetDTO("COMPUTER-01", 1);
        ITAsset expectedSavedAsset = new ITAsset("COMPUTER-01", AssetType.HARDWARE);
		expectedSavedAsset.setId("602d559c7c13bf5fd1894216");
		
		when(assetService.createAsset(expectAssetDTO)).thenReturn(expectedSavedAsset);

		mockMvc.perform(post("/ativos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(expectAssetDTO)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}
	
	@Test
	public void whenPOSTIsCalledWithAlreadRegisteredCategoryThenAnErrorIsReturned() throws Exception {
		ITAssetDTO expectAssetDTO = new ITAssetDTO("COMPUTER-01", 1);
		
		when(assetService.createAsset(expectAssetDTO)).thenThrow(ITAssetAlreadyRegisteredException.class);

		mockMvc.perform(post("/ativos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(expectAssetDTO)))
				.andExpect(status().isForbidden());
	}

    @Test
	public void whenGETListOfITAssetsIsCalledThenOKStatusIsReturned() throws Exception {
		List<ITAssetDTO> expectedFoundList =  
				Stream.of(new ITAssetDTO("COMPUTER-01", 1), new ITAssetDTO("SOFTWARE-01", 2)).collect(Collectors.toList());
		
		when(assetService.findAll()).thenReturn(expectedFoundList);
		
		mockMvc.perform(get("/ativos")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].tag", is("COMPUTER-01")))
		.andExpect(jsonPath("$[0].type", is(1)))
        .andExpect(jsonPath("$[1].tag", is("SOFTWARE-01")))
		.andExpect(jsonPath("$[1].type", is(2)));
		
	}
}
