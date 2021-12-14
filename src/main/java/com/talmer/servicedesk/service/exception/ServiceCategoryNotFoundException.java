package com.talmer.servicedesk.service.exception;

public class ServiceCategoryNotFoundException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

	public ServiceCategoryNotFoundException(String message) {
		super(message);
	}
}
