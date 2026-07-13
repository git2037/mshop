package com.mshop.app.common.core.exception;

public class ValidationException extends AppException {
    public ValidationException(ErrorCode code) {
        super(code);
    }

    public ValidationException(ErrorCode code, String message) {
        super(code, message);
    }
}
