package com.mshop.app.product.exception.product;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class ProductAttributeValueAlreadyExistException extends BusinessException {
    public ProductAttributeValueAlreadyExistException(ErrorCode code) {
        super(code);
    }

    public ProductAttributeValueAlreadyExistException(ErrorCode code, String message) {
        super(code, message);
    }
}
