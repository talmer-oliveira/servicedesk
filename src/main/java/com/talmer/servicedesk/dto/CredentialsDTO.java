package com.talmer.servicedesk.dto;

import javax.validation.constraints.Email;

public class CredentialsDTO {

	@Email(message = "Email inválido")
	private String email;
	
	private String password;

	public CredentialsDTO(@Email(message = "Email inválido") String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
