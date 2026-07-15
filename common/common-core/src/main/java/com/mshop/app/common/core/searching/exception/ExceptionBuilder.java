package com.mshop.app.common.core.searching.exception;

import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.ValidationException;

import java.util.Map;

public class ExceptionBuilder {
    private ExceptionBuilder() {
        /* This utility class should not be instantiated */
    }

    private static final String FIELD_VALUE = "field";

    public static ValidationException invalidKeyParam(ErrorCode code, String field) {
        return new ValidationException(code, Map.of(FIELD_VALUE, field));
    }

    public static ValidationException invalidValueParam(ErrorCode code, String field, String value) {
        return new ValidationException(code, Map.of(FIELD_VALUE, field, "value", value));
    }

    public static ValidationException invalidValueFilterParam(ErrorCode code, String field, String operator) {
        return new ValidationException(code, Map.of(FIELD_VALUE, field, "operator", operator));
    }
}
