package com.mshop.app.common.core.searching.filter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Operators {
    LIKE, EQUAL, GE, LE, IS_NULL;

    public static Operators fromString(String operator) {
        try {
            return Operators.valueOf(operator.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert request parameter to filter operator Enum. Invalid value provided: '{}'", operator);
            throw e;
        }
    }
}
