package com.talmer.servicedesk.resources;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.talmer.servicedesk.domain.ServiceCategory;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;
import com.talmer.servicedesk.service.ServiceCategoryService;
import com.talmer.servicedesk.service.exception.ServiceCategoryAlreadyRegisteredException;

@RestController
@RequestMapping(value="/categorias")
public class ServiceCategoryResource {

	@Autowired
	private ServiceCategoryService categoryService;
	
	@RequestMapping(method = POST)
	public ResponseEntity<Void> createCategory(@Valid @RequestBody ServiceCategoryDTO categoryDTO){
		try {
			ServiceCategory serviceCategory  = categoryService.createCategory(categoryDTO);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(serviceCategory.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (ServiceCategoryAlreadyRegisteredException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
