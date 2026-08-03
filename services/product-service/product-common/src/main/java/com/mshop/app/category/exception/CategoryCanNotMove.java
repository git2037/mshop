package com.mshop.app.category.exception;

import com.mshop.app.common.core.exception.BusinessException;
import com.mshop.app.common.core.exception.ErrorCode;

public class CategoryCanNotMove extends BusinessException {
    public CategoryCanNotMove(ErrorCode code) {
        super(code);
    }

    public CategoryCanNotMove(ErrorCode code, String message) {
        super(code, message);
    }
}
