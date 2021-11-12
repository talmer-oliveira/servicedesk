package com.talmer.servicedesk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.repository.UserRepository;
import com.talmer.servicedesk.security.AuthUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByEmail(username);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException(String.format("Usuário não encontrado para o email", username));
		}
		User user = optional.get();
		return new AuthUser(user.getId(), user.getEmail(), user.getPassword(), user.getActive(), user.getRoles());
	}
	
}
