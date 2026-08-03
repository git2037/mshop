package com.mshop.app.category.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class CategoryAlreadyExistException extends BusinessException {
    public CategoryAlreadyExistException(ErrorCode code) {
        super(code);
    }

    public CategoryAlreadyExistException(ErrorCode code, String message) {
        super(code, message);
    }
}
