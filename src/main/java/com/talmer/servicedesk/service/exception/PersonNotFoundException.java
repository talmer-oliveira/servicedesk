package com.talmer.servicedesk.service.exception;

public class PersonNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(String email) {
		super(String.format("Usuário não encontrado para o email ", email));
	}

}
