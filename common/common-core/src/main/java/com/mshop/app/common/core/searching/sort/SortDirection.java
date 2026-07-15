package com.mshop.app.common.core.searching.sort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SortDirection {
    ASC, DESC;

    public static SortDirection fromString(String s) {
        try {
            return SortDirection.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert parameter to sort direction Enum. Invalid value provided: '{}'", s);
            throw e;
        }
    }
}
