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
@Constraint(validatedBy = {NotBlankIfPresentValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "Field must not be blank if present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}