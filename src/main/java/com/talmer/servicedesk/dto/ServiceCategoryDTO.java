package com.talmer.servicedesk.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ServiceCategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(min = 1, max = 60)
	private String name;
	
	@NotNull
	private Integer categoryType;
	
	public ServiceCategoryDTO() {}

	public ServiceCategoryDTO(String name, Integer categoryType) {
		super();
		this.name = name;
		this.categoryType = categoryType;
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
}
