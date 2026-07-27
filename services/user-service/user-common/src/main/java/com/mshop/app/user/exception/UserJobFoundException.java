package com.mshop.app.user.exception;

import com.mshop.app.common.core.exception.AppException;
import com.mshop.app.common.core.exception.ErrorCode;

public class UserJobFoundException extends AppException {
    public UserJobFoundException(ErrorCode code) {
        super(code);
    }

    public UserJobFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
