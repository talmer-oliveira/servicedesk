package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User createUser(UserDTO userDTO) throws UserEmailAlreadyRegisteredException {
		verifyIfExists(userDTO.getEmail());
		User user = fromDTO(userDTO);
		User persistedUser = userRepository.save(user);
		return persistedUser;
	}
	
	private void verifyIfExists(String email) throws UserEmailAlreadyRegisteredException {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			throw new UserEmailAlreadyRegisteredException(email);
		}
	}
	
	private User fromDTO(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), userDTO.getName(), userDTO.getCpf(), userDTO.getPassword());
		return user;
	}
}
