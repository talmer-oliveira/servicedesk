package com.talmer.servicedesk.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.domain.enums.Role;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.dto.UserUpdateDTO;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.security.AuthUser;
import com.talmer.servicedesk.service.exception.ForbiddenException;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;
import com.talmer.servicedesk.service.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Spy
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Test
	public void whenPersonIsInformedThenItShouldBeCreated() throws UserEmailAlreadyRegisteredException {
		UserDTO expectedUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		User expectedSavedPerson = new User("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		
		when(userRepository.findByEmail(expectedUserDTO.getEmail())).thenReturn(Optional.empty());
		when(userRepository.save(expectedSavedPerson)).thenReturn(expectedSavedPerson);
		doReturn(passwordEncoder.encode(expectedUserDTO.getPassword())).when(passwordEncoder).encode(expectedUserDTO.getPassword());

		User savedPerson = userService.createUser(expectedUserDTO);
		
		assertThat(savedPerson.getEmail(), is(equalTo(expectedSavedPerson.getEmail())));
		assertThat(savedPerson.getName(), is(equalTo(expectedSavedPerson.getName())));
		assertThat(savedPerson.getActive(), is(equalTo(Boolean.FALSE)));
		assertThat(savedPerson.getRoles().size(), is(equalTo(1)));
		assertThat(savedPerson.getRoles(), contains(Role.USER));

	}
	
	@Test
	public void whenAnAlreadyRegisteredPersonIsInformedThenAnExceptionShouldBeThrown() {
		UserDTO expectedUserDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		User alreadyRegisteredUser = new User("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		
		when(userRepository.findByEmail(expectedUserDTO.getEmail())).thenReturn(Optional.of(alreadyRegisteredUser));
		
		assertThrows(UserEmailAlreadyRegisteredException.class, () -> userService.createUser(expectedUserDTO));
	}

	@Test
	public void whenFindByIdIsCalledByTheUserItselfThenReturnTheFoundUser() throws UserNotFoundException, ForbiddenException {
		UserDTO userDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		User expectedUser = new User("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		expectedUser.setId("sdjflkjdskfjdslfkj");
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		AuthUser authenticatedUser = new AuthUser("sdjflkjdskfjdslfkj", "teste-email@tmail.com", null, true, roles);
		
		when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);
		
		UserDTO returnedUserDTO = userService.findById(expectedUser.getId());
		
		assertThat(returnedUserDTO.getEmail(), is(equalTo(userDTO.getEmail())));
		assertThat(returnedUserDTO.getName(), is(equalTo(userDTO.getName())));
	}
	
	@Test
	public void whenFindByIdIsCalledByAnAdminUserThenReturnTheFoundUser() throws UserNotFoundException, ForbiddenException {
		UserDTO userDTO = new UserDTO("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		User expectedUser = new User("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		expectedUser.setId("sdjflkjdskfjdslfkj");
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		roles.add(Role.ADMIN);
		AuthUser authenticatedUser = new AuthUser("ssssssss", "teste-email@tmail.com", null, true, roles);
		
		when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);
		
		UserDTO returnedUserDTO = userService.findById(expectedUser.getId());
		
		assertThat(returnedUserDTO.getEmail(), is(equalTo(userDTO.getEmail())));
		assertThat(returnedUserDTO.getName(), is(equalTo(userDTO.getName())));
	}
	
	@Test
	public void whenFindByIdDoNotFindAnUserThenAnExceptionShouldBeThrown() {
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		roles.add(Role.ADMIN);
		AuthUser authenticatedUser = new AuthUser("ssssssss", "teste-email@tmail.com", null, true, roles);
		
		when(userRepository.findById("123456")).thenReturn(Optional.empty());
		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);
		
		assertThrows(UserNotFoundException.class, () -> userService.findById("123456"));
	}
	
	@Test
	public void whenUserDoNotHasPermissionToGetOtherUserDataThenAnExceptionShouldBeThrown() {
		User expectedUser = new User("teste-email@tmail.com", "Test Person", "01561607061", "o7,%kdy45LL?)p0");
		expectedUser.setId("sdjflkjdskfjdslfkj");
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		AuthUser authenticatedUser = new AuthUser("ssssssss", "teste-email@tmail.com", null, true, roles);
		
		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);
		
		assertThrows(ForbiddenException.class, () -> userService.findById(expectedUser.getId()));
	}

	@Test
	public void whenUpdateUserIsCalledByTheUserItselfThenUpdateTheUser(){
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO("teste.person@mail.com", "Test Person Change", "01561607061");
		User alreadyRegisteredUser = new User("teste-email@tmail.com", "Test Person", "01561607061");
		alreadyRegisteredUser.setId("617af8b8ca59013d804f0ac0");
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		AuthUser authenticatedUser = new AuthUser("617af8b8ca59013d804f0ac0", "teste-email@tmail.com", null, true, roles);
		User expectedUpdatedUser = new User("teste.person@mail.com", "Test Person Change", "01561607061");

		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);
		when(userRepository.findById(alreadyRegisteredUser.getId())).thenReturn(Optional.of(alreadyRegisteredUser));
		when(userRepository.findByEmail(userUpdateDTO.getEmail())).thenReturn(Optional.empty());
		when(userRepository.save(alreadyRegisteredUser)).thenReturn(expectedUpdatedUser);
		
		UserDTO updatedUser = userService.updateUser("617af8b8ca59013d804f0ac0", userUpdateDTO);

		assertThat(updatedUser.getEmail(), is(equalTo(userUpdateDTO.getEmail())));
		assertThat(updatedUser.getName(), is(equalTo(userUpdateDTO.getName())));
	}

	@Test
	public void whenUpdateUserIsCalledByOtherThanTheUserOwnerThenThrowAnException(){
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO("teste.person@mail.com", "Test Person Change", "01561607061");
		String anotherUserId = "617af8b8ca59013d804f0ac0";
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		AuthUser authenticatedUser = new AuthUser("617aff795aeb6e75aaf0dbb5", "teste-email@tmail.com", null, true, roles);
		
		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);

		assertThrows(ForbiddenException.class, () -> userService.updateUser(anotherUserId, userUpdateDTO));
	}

	@Test
	public void whenUpdateUserIsCalledWithAnotherUserEmailThenThrowAnException(){
		User user = new User("teste.person@mail.com", "Another Person", "01561607061");
		user.setId("617aff795aeb6e75aaf0dbb5");
		User anotherUser = new User("teste-email@tmail.com", "Test Person", "01561607061");
		anotherUser.setId("617af8b8ca59013d804f0ac0");
		UserUpdateDTO userUpdateDTO = new UserUpdateDTO("teste-email@tmail.com", "Another Person", "01561607061");
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		AuthUser authenticatedUser = new AuthUser("617aff795aeb6e75aaf0dbb5", "teste.person@mail.com", null, true, roles);

		when(userDetailsServiceImpl.authenticated()).thenReturn(authenticatedUser);
		when(userRepository.findByEmail(userUpdateDTO.getEmail())).thenReturn(Optional.of(anotherUser));

		ForbiddenException exception = assertThrows(ForbiddenException.class, () -> userService.updateUser(user.getId(), userUpdateDTO));
		assertThat("Email já cadastrado", is(equalTo(exception.getMessage())));
	}
}
