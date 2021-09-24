package com.talmer.servicedesk.domain.enums;

public enum Role {

	ADMIN(1, "ROLE_ADMIN"),
	DEV(2, "ROLE_DEV"),
	USER(3, "ROLE_USER");
	
	private final Integer code;
	
	private final String description;

	private Role(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
}
