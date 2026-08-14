package com.mshop.app.common.core.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(ErrorCode code) {
        super(code);
    }

    public ResourceNotFoundException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public ResourceNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }

    public ResourceNotFoundException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
