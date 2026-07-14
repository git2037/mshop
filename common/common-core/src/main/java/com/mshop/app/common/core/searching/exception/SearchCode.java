package com.mshop.app.common.core.searching.exception;

import com.mshop.app.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchCode implements ErrorCode {
    // query param error
    KEY_PARAM_NEEDS_TRIMMING("SEARCH_001", "The query parameter '{field}' is invalid."),
    KEY_PARAM_NOT_ALLOWED_UPPERCASE("SEARCH_002", "The query parameter '{field}' is invalid."),
    VALUE_NOT_BLANK("SEARCH_003", "The query value '{value}' is invalid for query parameter '{field}'."),

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
