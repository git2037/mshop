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

    // sort param error
    SORT_FIELD_NEED_TRIMMING("SEARCH_004", "The sort parameter '{field}' is invalid."),
    SORT_FIELD_NOT_CONTAIN_UPPERCASE("SEARCH_005", "The sort parameter '{field}' is invalid."),
    INVALID_SORT_PARAMETER_FORMAT("SEARCH_006", "Invalid sort parameter format: '{sortParam}'."),
    INVALID_SORT_FIELD("SEARCH_007", "The field '{field}' is not allowed for sorting."),
    INVALID_SORT_OPERATOR("SEARCH_008", "Invalid sort operator for field '{field}'. Only 'asc' or 'desc' are supported."),

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
