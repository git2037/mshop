package com.mshop.app.user.exception;

import com.mshop.app.common.core.exception.AppException;
import com.mshop.app.common.core.exception.ErrorCode;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(ErrorCode code) {
        super(code);
    }

    public UserNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
