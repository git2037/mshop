package com.mshop.app.product.exception.attribute;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class AttributeAlreadyDisableException extends BusinessException {
    public AttributeAlreadyDisableException(ErrorCode code) {
        super(code);
    }

    public AttributeAlreadyDisableException(ErrorCode code, String message) {
        super(code, message);
    }
}
