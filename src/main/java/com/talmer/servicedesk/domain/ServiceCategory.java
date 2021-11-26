package com.talmer.servicedesk.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.talmer.servicedesk.domain.enums.ServiceCategoryType;

@Document(collection = "service_category")
public class ServiceCategory implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;
	
	private Integer categoryType;
	
	public ServiceCategory() {}

	public ServiceCategory(String name, ServiceCategoryType categoryType) {
		super();
		this.name = name;
		setCategoryType(categoryType);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceCategoryType getCategoryType() {
		return ServiceCategoryType.toEnum(this.categoryType);
	}

	public void setCategoryType(ServiceCategoryType categoryType) {
		this.categoryType = categoryType.getCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ServiceCategory other = (ServiceCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
