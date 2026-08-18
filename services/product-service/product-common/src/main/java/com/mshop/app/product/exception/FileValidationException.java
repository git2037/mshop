package com.mshop.app.product.exception;

import com.mshop.app.common.core.exception.BadRequestException;
import com.mshop.app.common.core.exception.ErrorCode;

public class FileValidationException extends BadRequestException {
    private final String fileName;

    public FileValidationException(ErrorCode code, String fileName) {
        super(code);
        this.fileName = fileName;
    }

    public FileValidationException(ErrorCode code, String message, String fileName) {
        super(code, message);
        this.fileName = fileName;
    }
}
