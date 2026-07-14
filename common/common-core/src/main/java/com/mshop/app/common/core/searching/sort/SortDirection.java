package com.mshop.app.common.core.searching.sort;

import com.mshop.app.common.core.exception.ValidationException;
import com.mshop.app.common.core.searching.exception.SearchCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SortDirection {
    ASC, DESC;

    public static SortDirection fromString(String s) {
        try {
            return SortDirection.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert parameter to sort direction Enum. Invalid value provided: '{}'", s);
            throw new ValidationException(SearchCode.INVALID_SORT_OPERATOR);
        }
    }
}
