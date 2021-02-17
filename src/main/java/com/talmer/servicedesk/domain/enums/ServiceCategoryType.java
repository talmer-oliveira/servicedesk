package com.talmer.servicedesk.domain.enums;

public enum ServiceCategoryType {

	SERVICE_REQUEST(1, "Service Request");
	
	private final Integer code;
	
	private final String description;
	
	private ServiceCategoryType(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static ServiceCategoryType toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		for(ServiceCategoryType categoryType : ServiceCategoryType.values()) {
			if(categoryType.getCode().equals(code)) {
				return categoryType;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + code);
	}
}
