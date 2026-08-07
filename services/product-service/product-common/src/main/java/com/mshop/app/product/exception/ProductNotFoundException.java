package com.mshop.app.product.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(ErrorCode code) {
        super(code);
    }

    public ProductNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
