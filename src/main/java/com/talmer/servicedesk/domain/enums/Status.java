package com.talmer.servicedesk.domain.enums;

public enum Status {

	PENDING(1, "Aberta"),
	ANSWERING(2, "Em Atendimento"),
	FINISHED(3, "Atendida/Finalizada"),
	CANCELED(4, "Cancelada");
	
	private final Integer code;
	private final String description;
	
	private Status(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Status toEnum(Integer code) {
		if(code == null) {
			return null;
		}
		for(Status status : Status.values()) {
			if(status.getCode().equals(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + code);
	}
}
