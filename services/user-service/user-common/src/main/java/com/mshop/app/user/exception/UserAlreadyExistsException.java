package com.mshop.app.user.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(ErrorCode code) {
        super(code);
    }

    public UserAlreadyExistsException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public UserAlreadyExistsException(ErrorCode code, String message) {
        super(code, message);
    }

    public UserAlreadyExistsException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
