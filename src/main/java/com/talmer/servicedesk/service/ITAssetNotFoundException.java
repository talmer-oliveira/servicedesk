package com.talmer.servicedesk.service;

public class ITAssetNotFoundException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

	public ITAssetNotFoundException(String message) {
		super(message);
	}
}
