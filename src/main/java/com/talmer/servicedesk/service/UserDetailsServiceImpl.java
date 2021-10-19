package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.talmer.servicedesk.domain.Person;
import com.talmer.servicedesk.repository.PersonRepository;
import com.talmer.servicedesk.security.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> optional = personRepository.findByEmail(username);
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException(String.format("Usuário não encontrado para o email", username));
		}
		Person person = optional.get();
		return new User(person.getId(), person.getEmail(), person.getPassword(), false, person.getRoles());
	}

}
