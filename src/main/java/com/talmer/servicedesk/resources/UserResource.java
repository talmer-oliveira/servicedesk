package com.talmer.servicedesk.resources;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.talmer.servicedesk.domain.User;
import com.talmer.servicedesk.dto.UserDTO;
import com.talmer.servicedesk.service.UserService;
import com.talmer.servicedesk.service.exception.UserEmailAlreadyRegisteredException;

@RestController
@RequestMapping(value="/usuarios")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO){
		try {
			User user = userService.createUser(userDTO);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}").buildAndExpand(user.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (UserEmailAlreadyRegisteredException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}
}