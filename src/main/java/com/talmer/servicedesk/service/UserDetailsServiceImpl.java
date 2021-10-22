package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.security.AuthUser;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository personRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = personRepository.findByEmail(username);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException(String.format("Usuário não encontrado para o email", username));
		}
		User person = optional.get();
		return new AuthUser(person.getId(), person.getEmail(), person.getPassword(), false, person.getRoles());
	}

}
