package com.talmer.servicedesk.service.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String email) {
		super(String.format("Usuário não encontrado para o email ", email));
	}

}
