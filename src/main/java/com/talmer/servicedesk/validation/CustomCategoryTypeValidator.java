package com.talmer.servicedesk.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talmer.servicedesk.domain.enums.ServiceCategoryType;
import com.talmer.servicedesk.dto.ServiceCategoryDTO;

public class CustomCategoryTypeValidator implements ConstraintValidator<ValidCategoryType, ServiceCategoryDTO> {

	@Override
	public boolean isValid(ServiceCategoryDTO value, ConstraintValidatorContext context) {
		try {
			ServiceCategoryType categoryType = ServiceCategoryType.toEnum(value.getCategoryType());
			if(categoryType != null) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode("categoryType")
				.addConstraintViolation();
			return false;
		}
		return false;
	}

}
