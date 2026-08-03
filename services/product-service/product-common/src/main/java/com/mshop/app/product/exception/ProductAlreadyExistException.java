package com.mshop.app.product.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class ProductAlreadyExistException extends BusinessException {
    public ProductAlreadyExistException(ErrorCode code) {
        super(code);
    }

    public ProductAlreadyExistException(ErrorCode code, String message) {
        super(code, message);
    }
}
