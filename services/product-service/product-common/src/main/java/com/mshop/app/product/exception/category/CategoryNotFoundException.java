package com.mshop.app.product.exception.category;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(ErrorCode code) {
        super(code);
    }

    public CategoryNotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
}
