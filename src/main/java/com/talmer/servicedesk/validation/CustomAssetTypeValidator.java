package com.talmer.servicedesk.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talmer.servicedesk.domain.enums.AssetType;
import com.talmer.servicedesk.dto.ITAssetDTO;

public class CustomAssetTypeValidator implements ConstraintValidator<ValidAssetType, ITAssetDTO>{

    @Override
    public boolean isValid(ITAssetDTO value, ConstraintValidatorContext context) {
        try {
			AssetType assetType = AssetType.toEnum(value.getType());
			if(assetType != null) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode("assetType")
				.addConstraintViolation();
			return false;
		}
		return false;
    }
    
}
