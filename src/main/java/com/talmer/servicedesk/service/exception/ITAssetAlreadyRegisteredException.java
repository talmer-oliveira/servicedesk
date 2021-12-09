package com.talmer.servicedesk.service.exception;

public class ITAssetAlreadyRegisteredException extends Exception{

	private static final long serialVersionUID = 1L;

	public ITAssetAlreadyRegisteredException(String assetTag) {
        super(String.format("Ativo com etiqueta %s já existe no sistema.", assetTag));
    }
    
}
