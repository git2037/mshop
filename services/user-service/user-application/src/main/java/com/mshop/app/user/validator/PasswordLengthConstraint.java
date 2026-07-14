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
@Constraint(validatedBy = {PasswordLengthValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PasswordLengthConstraint {

    String message() default "PASSWORD_MIN_LENGTH";
    int min() default 8;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
