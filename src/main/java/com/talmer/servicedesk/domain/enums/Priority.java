package com.talmer.servicedesk.domain.enums;

public enum Priority {

	LOW(1, "Baixa"),
	MEDIUM(2, "Média"),
	HIGH(3, "Alta");
	
	private final Integer code;
	private final String description;
	
	private Priority(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Priority toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		for(Priority prioridade : Priority.values()) {
			if(prioridade.getCode().equals(code)) {
				return prioridade;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + code);
	}
	
}
