package com.talmer.servicedesk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talmer.servicedesk.domain.Person;
import com.talmer.servicedesk.domain.enums.Role;
import com.talmer.servicedesk.dto.PersonDTO;
import com.talmer.servicedesk.repository.PersonRepository;
import com.talmer.servicedesk.service.exception.PersonEmailAlreadyRegisteredException;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PersonService personService;
	
	@Test
	public void whenPersonIsInformedThenItShouldBeCreated() throws PersonEmailAlreadyRegisteredException {
		PersonDTO expectedPersonDTO = new PersonDTO("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		Person expectedSavedPerson = new Person("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		
		when(personRepository.findByEmail(expectedPersonDTO.getEmail())).thenReturn(Optional.empty());
		when(personRepository.save(expectedSavedPerson)).thenReturn(expectedSavedPerson);
		
		Person savedPerson = personService.insertPerson(expectedPersonDTO);
		
		assertThat(savedPerson.getEmail(), is(equalTo(expectedSavedPerson.getEmail())));
		assertThat(savedPerson.getName(), is(equalTo(expectedSavedPerson.getName())));
		assertThat(savedPerson.getActive(), is(equalTo(Boolean.FALSE)));
		assertThat(savedPerson.getRoles().size(), is(equalTo(1)));
		assertThat(savedPerson.getRoles(), Matchers.contains(Role.USER));
	}
	
	@Test
	public void whenAnAlreadyRegisteredPersonIsInformedThenAnExceptionShouldBeThrown() {
		PersonDTO expectedPersonDTO = new PersonDTO("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		Person alreadyRegisteredPerson = new Person("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		
		when(personRepository.findByEmail(expectedPersonDTO.getEmail())).thenReturn(Optional.of(alreadyRegisteredPerson));
		
		Assertions.assertThrows(PersonEmailAlreadyRegisteredException.class, () -> personService.insertPerson(expectedPersonDTO));
	}

}
