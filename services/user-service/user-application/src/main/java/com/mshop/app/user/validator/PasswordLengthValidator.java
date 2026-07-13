package com.mshop.app.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordLengthValidator implements ConstraintValidator<PasswordLengthConstraint, String> {
    private int min;

    @Override
    public void initialize(PasswordLengthConstraint constraintAnnotation) {
        this.min = constraintAnnotation.min();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank())
            return true;

        return value.length() >= min;
    }
}
