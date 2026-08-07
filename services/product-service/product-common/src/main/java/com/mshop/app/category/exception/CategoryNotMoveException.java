package com.mshop.app.category.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class CategoryNotMoveException extends BusinessException {
    public CategoryNotMoveException(ErrorCode code) {
        super(code);
    }

    public CategoryNotMoveException(ErrorCode code, String message) {
        super(code, message);
    }
}
