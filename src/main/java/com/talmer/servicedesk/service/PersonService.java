package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.Person;
import com.talmer.servicedesk.dto.PersonDTO;
import com.talmer.servicedesk.repository.PersonRepository;
import com.talmer.servicedesk.service.exception.PersonEmailAlreadyRegisteredException;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public Person insertPerson(PersonDTO personDTO) throws PersonEmailAlreadyRegisteredException {
		verifyIfExists(personDTO.getEmail());
		Person person = fromDTO(personDTO);
		Person persistedPerson = personRepository.save(person);
		return persistedPerson;
	}
	
	private void verifyIfExists(String email) throws PersonEmailAlreadyRegisteredException {
		Optional<Person> person = personRepository.findByEmail(email);
		if(person.isPresent()) {
			throw new PersonEmailAlreadyRegisteredException(email);
		}
	}
	
	private Person fromDTO(PersonDTO personDTO) {
		Person person = new Person(personDTO.getEmail(), personDTO.getName(), personDTO.getCpf(), personDTO.getPassword());
		return person;
	}
}
