package com.mshop.app.common.core.exception;

public class ConflictException extends BusinessException {
    public ConflictException(ErrorCode code) {
        super(code);
    }

    public ConflictException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public ConflictException(ErrorCode code, String message) {
        super(code, message);
    }

    public ConflictException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
