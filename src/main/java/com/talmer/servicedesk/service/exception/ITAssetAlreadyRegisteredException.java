package com.talmer.servicedesk.service.exception;

public class ITAssetAlreadyRegisteredException extends Exception{

    public ITAssetAlreadyRegisteredException(String assetTag) {
        super(String.format("Ativo com etiqueta %s já existe no sistema.", assetTag));
    }
    
}
