package com.talmer.servicedesk.service.exception;

public class ServiceCategoryAlreadyRegisteredException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceCategoryAlreadyRegisteredException(String categoryName) {
		super(String.format("Categoria com nome %s já existe no sistema.", categoryName));
	}
}
