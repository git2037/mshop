package com.mshop.app.user.validator;

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
    String message() default "FULL_NAME_NOT_BLANK";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}