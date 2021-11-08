package com.talmer.servicedesk.service.exception;

public class UserEmailAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserEmailAlreadyRegisteredException(String email) {
		super(String.format("O endereço de email %s já existe no sistema.", email));
	}
}
