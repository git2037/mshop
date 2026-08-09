package com.mshop.app.common.core.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ValueOfEnumValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid value of enum!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}