package com.mshop.app.attribute.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class AttributeAlreadyExistException extends BusinessException {
    public AttributeAlreadyExistException(ErrorCode code) {
        super(code);
    }

    public AttributeAlreadyExistException(ErrorCode code, String message) {
        super(code, message);
    }
}
