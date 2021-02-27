package com.talmer.servicedesk.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.talmer.servicedesk.validation.ValidCategoryType;

@ValidCategoryType
public class ServiceCategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty(access = Access.READ_ONLY)
	private String id;
	
	@NotNull(message = "Não pode ser nulo")
	@Size(min = 1, max = 60, message = "Deve ter entre 1 e 60 caracteres")
	private String name;
	
	private Integer categoryType;
	
	public ServiceCategoryDTO() {}

	public ServiceCategoryDTO(String name, Integer categoryType) {
		super();
		this.name = name;
		this.categoryType = categoryType;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getCategoryType() {
		return categoryType;
	}
	
	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryType == null) ? 0 : categoryType.hashCode());
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
		ServiceCategoryDTO other = (ServiceCategoryDTO) obj;
		if (categoryType == null) {
			if (other.categoryType != null)
				return false;
		} else if (!categoryType.equals(other.categoryType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
