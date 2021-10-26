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

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.domain.enums.Role;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void whenPersonIsInformedThenItShouldBeCreated() throws UserEmailAlreadyRegisteredException {
		UserDTO expectedUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		User expectedSavedPerson = new User("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		
		when(userRepository.findByEmail(expectedUserDTO.getEmail())).thenReturn(Optional.empty());
		when(userRepository.save(expectedSavedPerson)).thenReturn(expectedSavedPerson);
		
		User savedPerson = userService.createUser(expectedUserDTO);
		
		assertThat(savedPerson.getEmail(), is(equalTo(expectedSavedPerson.getEmail())));
		assertThat(savedPerson.getName(), is(equalTo(expectedSavedPerson.getName())));
		assertThat(savedPerson.getActive(), is(equalTo(Boolean.FALSE)));
		assertThat(savedPerson.getRoles().size(), is(equalTo(1)));
		assertThat(savedPerson.getRoles(), Matchers.contains(Role.USER));
	}
	
	@Test
	public void whenAnAlreadyRegisteredPersonIsInformedThenAnExceptionShouldBeThrown() {
		UserDTO expectedUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		User alreadyRegisteredUser = new User("teste-email@tmail.com", "Test Person", "10846868644", "o7,%kdy45LL?)p0");
		
		when(userRepository.findByEmail(expectedUserDTO.getEmail())).thenReturn(Optional.of(alreadyRegisteredUser));
		
		Assertions.assertThrows(UserEmailAlreadyRegisteredException.class, () -> userService.createUser(expectedUserDTO));
	}

}
