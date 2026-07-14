package com.mshop.app.common.core.exception;

import com.mshop.app.common.core.searching.exception.MessageBuilder;

import java.util.Map;

public class ValidationException extends AppException {
    public ValidationException(ErrorCode code) {
        super(code);
    }

    public ValidationException(ErrorCode code, String message) {
        super(code, message);
    }

    public ValidationException(ErrorCode code, Map<String, Object> params) {
        super(code, MessageBuilder.format(code.getMessage(), params));
    }
}
