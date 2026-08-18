package com.mshop.app.product.exception.attribute;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class AttributeValueAlreadyExistException extends BusinessException {
    public AttributeValueAlreadyExistException(ErrorCode code) {
        super(code);
    }

    public AttributeValueAlreadyExistException(ErrorCode code, String message) {
        super(code, message);
    }
}
