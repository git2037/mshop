package com.mshop.app.common.core.exception;

public class BusinessException extends AppException {
    public BusinessException(ErrorCode code) {
        super(code);
    }

    public BusinessException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public BusinessException(ErrorCode code, String message) {
        super(code, message);
    }

    public BusinessException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
