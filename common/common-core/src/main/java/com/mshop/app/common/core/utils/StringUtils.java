package com.mshop.app.common.core.utils;

import com.mshop.app.common.core.searching.exception.ExceptionBuilder;
import com.mshop.app.common.core.searching.exception.SearchCode;

public class StringUtils {
    private StringUtils() {
        /* This utility class should not be instantiated */
    }


    public static String kebabCaseToCamelCase(String kebabCase) {
        if (kebabCase == null || kebabCase.isBlank())
            throw ExceptionBuilder.invalidKeyParam(SearchCode.INVALID_SEARCH_FIELD, kebabCase);

        String[] parts = kebabCase.split("-");

        if (parts.length == 1)
            return kebabCase;

        StringBuilder sb = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            sb.append(capitalize(parts[i]));
        }

        return sb.toString();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
