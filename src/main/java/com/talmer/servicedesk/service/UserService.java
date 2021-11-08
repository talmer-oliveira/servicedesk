package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.domain.enums.Role;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.security.AuthUser;
import com.talmer.servicedesk.service.exception.UnauthorizedException;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;
import com.talmer.servicedesk.service.exception.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	public User createUser(UserDTO userDTO){
		verifyIfExists(userDTO.getEmail());
		User user = fromDTO(userDTO);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User persistedUser = userRepository.save(user);
		return persistedUser;
	}
	
	public UserDTO findById(String id) throws UserNotFoundException, UnauthorizedException {
		AuthUser authenticated = userDetailsServiceImpl.authenticated();
		if(id == null || !authenticated.getId().equals(id) && !authenticated.hasRole(Role.ADMIN)) {
			throw new UnauthorizedException("Você não tem permissão para acessar esse recurso");
		}
		Optional<User> user = userRepository.findById(id);
		return instantiateIfIsUserPresent(user, id);
	}
	
	public UserDTO findByEmail(String email) throws UserNotFoundException, UnauthorizedException {
		AuthUser authenticated = userDetailsServiceImpl.authenticated();
		if(email == null || !authenticated.getUsername().equals(email) && !authenticated.hasRole(Role.ADMIN)) {
			throw new UnauthorizedException("Você não tem permissão para acessar esse recurso");
		}
		Optional<User> user = userRepository.findByEmail(email);
		return instantiateIfIsUserPresent(user, email);
	}
	
	private void verifyIfExists(String email) throws UserEmailAlreadyRegisteredException {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			throw new UserEmailAlreadyRegisteredException(email);
		}
	}
	
	private UserDTO toDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setCpf(user.getCpf());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		return userDTO;
	}
	
	private User fromDTO(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), userDTO.getName(), userDTO.getCpf(), userDTO.getPassword());
		return user;
	}
	
	private UserDTO instantiateIfIsUserPresent(Optional<User> user, String atributte) {
		if(user.isPresent()) {
			return toDTO(user.get());
		}
		throw new UserNotFoundException("Usuário não encontrado: " + atributte);
	}
}
