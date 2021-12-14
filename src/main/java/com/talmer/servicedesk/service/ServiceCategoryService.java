package com.talmer.servicedesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.ServiceCategory;
import com.talmer.servicedesk.domain.enums.ServiceCategoryType;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.repository.ServiceCategoryRepository;
import com.talmer.servicedesk.service.exception.ServiceCategoryAlreadyRegisteredException;
import com.talmer.servicedesk.service.exception.ServiceCategoryNotFoundException;

@Service
public class ServiceCategoryService {

	@Autowired
	private ServiceCategoryRepository repository;
	
	public ServiceCategory createCategory(ServiceCategoryDTO categoryDTO) throws ServiceCategoryAlreadyRegisteredException {
		verifyIfExists(categoryDTO.getName());
		ServiceCategory serviceCategory = fromDTO(categoryDTO);
		ServiceCategory savedCategory = repository.save(serviceCategory);
		return savedCategory;
	}
	
	public ServiceCategoryDTO findByName(String name){
		Optional<ServiceCategory> optSavedCategory = repository.findByName(name);
		if(optSavedCategory.isPresent()){
			ServiceCategory serviceCategory = optSavedCategory.get();
			return new ServiceCategoryDTO(serviceCategory.getName(), serviceCategory.getCategoryType().getCode());
		}
		else{
			throw new ServiceCategoryNotFoundException("Categoria de Serviço não encontrada: " + name);
		}
	}

	public List<ServiceCategoryDTO> findAll(){
		return repository.findAll()
						.stream()
						.map(cat -> new ServiceCategoryDTO(cat.getName(), cat.getCategoryType().getCode()))
						.collect(Collectors.toList());
	}
	
	private void verifyIfExists(String name) throws ServiceCategoryAlreadyRegisteredException {
		Optional<ServiceCategory> optSavedCategory = repository.findByName(name);
		if(optSavedCategory.isPresent()) {
			throw new ServiceCategoryAlreadyRegisteredException(name);
		}
	}
	
	public ServiceCategory fromDTO(ServiceCategoryDTO categoryDTO) {
		return new ServiceCategory(categoryDTO.getName(),ServiceCategoryType.toEnum(categoryDTO.getCategoryType()));
	}
	
	public ServiceCategoryDTO toDTO(ServiceCategory serviceCategory) {
		return new ServiceCategoryDTO(serviceCategory.getName(), serviceCategory.getCategoryType().getCode());
	}
}
