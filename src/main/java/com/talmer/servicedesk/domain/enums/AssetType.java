package com.talmer.servicedesk.domain.enums;

public enum AssetType {
    
    HARDWARE(1, "Hardware"),
    SOFTWARE(2, "Software");

    private final Integer code;
	
	private final String description;

    private AssetType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
    }

    public static AssetType toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		for(AssetType assetType : AssetType.values()) {
			if(assetType.getCode().equals(code)) {
				return assetType;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + code);
	}
}
