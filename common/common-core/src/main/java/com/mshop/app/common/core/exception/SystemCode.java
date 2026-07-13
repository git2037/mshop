package com.mshop.app.common.core.exception;

import com.mshop.app.common.core.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SystemCode implements ErrorCode {
    UNEXPECTED_ERROR("SYS_001", ErrorMessage.GENERIC),

    VALIDATION_ERROR("VALIDATION_ERROR", "Invalid input data."),
    ;

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
