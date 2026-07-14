package com.mshop.app.common.core.searching.validator;

import com.mshop.app.common.core.searching.exception.ExceptionBuilder;
import com.mshop.app.common.core.searching.exception.SearchCode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class QueryParamValidator {

    private QueryParamValidator() {
    }

    private static final Set<String> PAGINATION_PARAMS = Set.of(
            "page", "size", "sort"
    );

    public static Map<String, String> removePaginationParams(Map<String, String> params) {
        if (params == null || params.isEmpty())
            return Collections.emptyMap();

        params.keySet().removeAll(PAGINATION_PARAMS);
        return params;
    }

    public static Map<String, String> validateParams(Map<String, String> params) {
        if (params == null || params.isEmpty())
            return Collections.emptyMap();

        Map<String, String> result = new LinkedHashMap<>();
        params.forEach((key, value) -> {
            validateFieldAndValue(key, value);
            result.put(key, value);
        });

        return result;
    }

    private static void validateFieldAndValue(String field, String value) {
        if (!field.equals(field.toLowerCase()))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.KEY_PARAM_NOT_ALLOWED_UPPERCASE, field);

        if (!field.equals(field.trim()))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.KEY_PARAM_NEEDS_TRIMMING, field);

        if (value == null || value.isBlank())
            throw ExceptionBuilder.invalidValueParam(SearchCode.VALUE_NOT_BLANK, field, value);
    }
}
