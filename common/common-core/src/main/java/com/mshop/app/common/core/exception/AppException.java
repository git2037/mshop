package com.mshop.app.common.core.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode code;

    public AppException(ErrorCode code) {
        this.code = code;
    }

    public AppException(ErrorCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.code = code;
    }

    public AppException(ErrorCode code, String message) {
        super(message == null ? code.getMessage() : message);
        this.code = code;
    }

    public AppException(ErrorCode code, String message, Throwable cause) {
        super(message == null ? code.getMessage() : message, cause);
        this.code = code;
    }
}
