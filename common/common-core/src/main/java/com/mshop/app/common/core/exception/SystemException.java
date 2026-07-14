package com.mshop.app.common.core.exception;

public class SystemException extends AppException {
    public SystemException(ErrorCode code) {
        super(code);
    }

    public SystemException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public SystemException(ErrorCode code, String message) {
        super(code, message);
    }

    public SystemException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
