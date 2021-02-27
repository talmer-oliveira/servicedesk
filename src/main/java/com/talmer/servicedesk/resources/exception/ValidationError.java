package com.talmer.servicedesk.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String error;
	
	private List<FieldErrorMessage> fieldsMessages;

	public ValidationError(Integer status, String error) {
		super();
		this.status = status;
		this.error = error;
		fieldsMessages = new ArrayList<>();
	}

	public void addFieldError(String field, String messsage) {
		fieldsMessages.add(new FieldErrorMessage(field, messsage));
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<FieldErrorMessage> getFieldsMessages() {
		return fieldsMessages;
	}

}
