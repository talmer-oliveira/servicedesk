package com.talmer.servicedesk.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class UserUpdateDTO {
	
	@Email(message = "Email com formato inválido")
	@NotNull(message= "Preenchimento obrigatório")
	private String email;
	
	@NotNull(message= "Preenchimento obrigatório")
	@Size(min = 3, max = 100, message = "Deve ter entre 1 e 100 caracteres")
	private String name;
	
	@CPF(message= "CPF com formato inválido")
	private String cpf;

	public UserUpdateDTO() {}
	
	public UserUpdateDTO(String email, String name, String cpf) {
		super();
		this.email = email;
		this.name = name;
		this.cpf = cpf;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserUpdateDTO other = (UserUpdateDTO) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
