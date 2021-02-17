package com.talmer.servicedesk.service;

import static com.talmer.servicedesk.domain.enums.ServiceCategoryType.SERVICE_REQUEST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talmer.servicedesk.domain.ServiceCategory;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.repository.ServiceCategoryRepository;
import com.talmer.servicedesk.service.exception.ServiceCategoryAlreadyRegisteredException;

@ExtendWith(MockitoExtension.class)
public class ServiceCategoryServiceTest {

	@Mock
	private ServiceCategoryRepository repository;
	
	@InjectMocks
	private ServiceCategoryService service;
	
	@Test
	public void whenServiceCategoryIsInformedThenItShouldBeCreated() throws ServiceCategoryAlreadyRegisteredException {
		ServiceCategoryDTO expectedCategoryDTO = 
				new ServiceCategoryDTO("Criação de Funcionalidade", 1);
		ServiceCategory expectedSavedCategory = new ServiceCategory("Criação de Funcionalidade", SERVICE_REQUEST);
		
		when(repository.findByName(expectedCategoryDTO.getName())).thenReturn(Optional.empty());
		when(repository.save(expectedSavedCategory)).thenReturn(expectedSavedCategory);
		
		ServiceCategory savedCategory = service.createCategory(expectedCategoryDTO);
		
		assertThat(savedCategory.getName(), is(CoreMatchers.equalTo(expectedCategoryDTO.getName())));
		assertThat(savedCategory.getCategoryType(), is(CoreMatchers.equalTo(expectedCategoryDTO.getCategoryType())));
	}
	
	@Test
	public void whenAlreadyRegisteredServiceCategoryIsInformedThenAnExceptionShouldBeThrown(){
		ServiceCategoryDTO expectedCategoryDTO = 
				new ServiceCategoryDTO("Criação de Funcionalidade", 1);
		ServiceCategory duplicatedSavedCategory = new ServiceCategory("Criação de Funcionalidade", SERVICE_REQUEST);
		
		when(repository.findByName(expectedCategoryDTO.getName())).thenReturn(Optional.of(duplicatedSavedCategory));
		
		Assertions.assertThrows(ServiceCategoryAlreadyRegisteredException.class, () -> service.createCategory(expectedCategoryDTO));
	}
}
