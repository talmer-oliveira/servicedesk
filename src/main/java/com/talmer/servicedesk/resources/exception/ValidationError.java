package com.talmer.servicedesk.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String error;
	
	private List<FieldErrorMessage> errorMessages;

	public ValidationError(Integer status, String error) {
		super();
		this.status = status;
		this.error = error;
		errorMessages = new ArrayList<>();
	}

	public void addFieldError(String field, String messsage) {
		errorMessages.add(new FieldErrorMessage(field, messsage));
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

	public List<FieldErrorMessage> getErrorMessages() {
		return errorMessages;
	}

}
