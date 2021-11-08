package com.talmer.servicedesk.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private String id;
	
	@Email(message = "Email com formato inválido")
	@NotNull(message= "Preenchimento obrigatório")
	private String email;
	
	@NotNull(message= "Preenchimento obrigatório")
	@Size(min = 3, max = 100, message = "Deve ter entre 1 e 100 caracteres")
	private String name;
	
	@CPF(message= "CPF com formato inválido")
	private String cpf;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}"
			,message = "Não satisfaz os requisitos de segurança")
	private String password;

	public UserDTO() {}
	
	public UserDTO(String email, String name, String cpf, String password) {
		super();
		this.email = email;
		this.name = name;
		this.cpf = cpf;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
