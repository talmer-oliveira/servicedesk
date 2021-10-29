package com.talmer.servicedesk.resources.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationError
									(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError error = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação");
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			error.addFieldError(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.unprocessableEntity().body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardResponseError> handleGenericException(Exception e, HttpServletRequest request){
		StandardResponseError error = 
					new StandardResponseError(
									System.currentTimeMillis(), INTERNAL_SERVER_ERROR.value(), "Erro no servidor", e.getMessage()
									,request.getRequestURI());
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
	}

}
