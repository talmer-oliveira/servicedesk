package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.ServiceCategory;
import com.talmer.servicedesk.domain.enums.ServiceCategoryType;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.repository.ServiceCategoryRepository;
import com.talmer.servicedesk.service.exception.ServiceCategoryAlreadyRegisteredException;

@Service
public class ServiceCategoryService {

	@Autowired
	private ServiceCategoryRepository repository;
	
	public ServiceCategory createCategory(ServiceCategoryDTO categoryDTO) throws ServiceCategoryAlreadyRegisteredException {
		verityIfExists(categoryDTO.getName());
		ServiceCategory serviceCategory = fromDTO(categoryDTO);
		ServiceCategory savedCategory = repository.save(serviceCategory);
		return savedCategory;
	}
	
	private void verityIfExists(String name) throws ServiceCategoryAlreadyRegisteredException {
		Optional<ServiceCategory> optSavedCategory = repository.findByName(name);
		if(optSavedCategory.isPresent()) {
			throw new ServiceCategoryAlreadyRegisteredException(name);
		}
	}
	
	public ServiceCategory fromDTO(ServiceCategoryDTO categoryDTO) {
		return new ServiceCategory(categoryDTO.getName(), ServiceCategoryType.toEnum(categoryDTO.getCategoryType()));
	}
	
	public ServiceCategoryDTO toDTO(ServiceCategory serviceCategory) {
		return new ServiceCategoryDTO(serviceCategory.getName(), serviceCategory.getCategoryType().getCode());
	}
}
