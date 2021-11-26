package com.talmer.servicedesk.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CustomAssetTypeValidator.class)
@Target( {ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAssetType {

    String message() default "Tipo inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
