package com.mshop.app.product.exception.category;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class CategoryNotLeafException extends BusinessException {
    public CategoryNotLeafException(ErrorCode code) {
        super(code);
    }

    public CategoryNotLeafException(ErrorCode code, String message) {
        super(code, message);
    }
}
