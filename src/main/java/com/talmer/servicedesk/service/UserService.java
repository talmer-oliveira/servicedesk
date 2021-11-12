package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.domain.enums.Role;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.dto.UserUpdateDTO;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.security.AuthService;
import com.talmer.servicedesk.security.AuthUser;
import com.talmer.servicedesk.service.exception.ForbiddenException;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;
import com.talmer.servicedesk.service.exception.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthService authService;
	
	public User createUser(UserDTO userDTO){
		verifyIfExists(userDTO.getEmail());
		User user = fromDTO(userDTO);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User persistedUser = userRepository.save(user);
		return persistedUser;
	}
	
	public UserDTO updateUser(String id, UserUpdateDTO userUpdateDTO) {
		checkIdAccessPermission(id);

		Optional<User> foundByEmail = userRepository.findByEmail(userUpdateDTO.getEmail());
		if(foundByEmail.isPresent() && !foundByEmail.get().getId().equals(id)) {
			throw new ForbiddenException("Email já cadastrado");
		}

		Optional<User> optionalUser = userRepository.findById(id);
		User userToUpdate = updateFromDTO(userUpdateDTO, optionalUser.get());
		return toDTO(userRepository.save(userToUpdate));
	}
	
	public UserDTO findById(String id){
		checkIdAccessPermission(id);
		Optional<User> user = userRepository.findById(id);
		return instantiateIfIsUserPresent(user, id);
	}
	
	public UserDTO findByEmail(String email){
		AuthUser authenticated = authService.authenticated();
		if(email == null || !authenticated.getUsername().equals(email) && !authenticated.hasRole(Role.ADMIN)) {
			throw new ForbiddenException("Você não tem permissão para acessar esse recurso");
		}
		Optional<User> user = userRepository.findByEmail(email);
		return instantiateIfIsUserPresent(user, email);
	}
	
	private void checkIdAccessPermission(String id) {
		AuthUser authenticated = authService.authenticated();
		if(id == null || !authenticated.getId().equals(id) && !authenticated.hasRole(Role.ADMIN)) {
			throw new ForbiddenException("Você não tem permissão para acessar esse recurso");
		}
	}
	
	private void verifyIfExists(String email) throws UserEmailAlreadyRegisteredException {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			throw new UserEmailAlreadyRegisteredException(email);
		}
	}
	
	private UserDTO toDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setCpf(user.getCpf());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		return userDTO;
	}
	
	private User fromDTO(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), userDTO.getName(), userDTO.getCpf(), userDTO.getPassword());
		return user;
	}
	
	private User updateFromDTO(UserUpdateDTO userDTO, User user) {
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setCpf(userDTO.getCpf());
		return user;
	}
	
	private UserDTO instantiateIfIsUserPresent(Optional<User> user, String atributte) {
		if(user.isPresent()) {
			return toDTO(user.get());
		}
		throw new UserNotFoundException("Usuário não encontrado: " + atributte);
	}
}
