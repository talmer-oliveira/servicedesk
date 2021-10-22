package com.talmer.servicedesk.service.exception;

public class UserEmailAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserEmailAlreadyRegisteredException(String categoryName) {
		super(String.format("O endereço de email %s já existe no sistema.", categoryName));
	}
}
