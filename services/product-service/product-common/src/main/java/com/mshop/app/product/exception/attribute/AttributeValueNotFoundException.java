package com.mshop.app.product.exception.attribute;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class AttributeValueNotFoundException extends BusinessException {
    public AttributeValueNotFoundException(ErrorCode code) {
        super(code);
    }

    public AttributeValueNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
