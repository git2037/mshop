package com.mshop.app.attribute.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class AttributeNotFoundException extends BusinessException {
    public AttributeNotFoundException(ErrorCode code) {
        super(code);
    }

    public AttributeNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
