package com.mshop.app.common.core.exception;

public class BadRequestException extends BusinessException {
    public BadRequestException(ErrorCode code) {
        super(code);
    }

    public BadRequestException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public BadRequestException(ErrorCode code, String message) {
        super(code, message);
    }

    public BadRequestException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
