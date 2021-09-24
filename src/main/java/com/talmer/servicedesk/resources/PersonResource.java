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

import com.talmer.servicedesk.domain.Person;
import com.talmer.servicedesk.dto.PersonDTO;
import com.talmer.servicedesk.service.PersonService;
import com.talmer.servicedesk.service.exception.PersonEmailAlreadyRegisteredException;

@RestController
@RequestMapping(value="/usuarios")
public class PersonResource {

	@Autowired
	private PersonService personService;
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> insertPerson(@Valid @RequestBody PersonDTO personDTO){
		try {
			Person person = personService.insertPerson(personDTO);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}").buildAndExpand(person.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (PersonEmailAlreadyRegisteredException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
}
