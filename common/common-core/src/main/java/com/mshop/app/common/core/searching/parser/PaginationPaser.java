package com.mshop.app.common.core.searching.parser;

import com.mshop.app.common.core.constant.PaginationConstants;
import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.ValidationException;
import com.mshop.app.common.core.searching.exception.SearchCode;
import com.mshop.app.common.core.searching.model.Pagination;

import java.util.Map;

public class PaginationPaser {
    private PaginationPaser() {
        /* This utility class should not be instantiated */
    }

    public static Pagination parse(Map<String, String> queryParams) {
        int page = parseIntOrDefault(queryParams.get("page"), PaginationConstants.DEFAULT_PAGE, SearchCode.INVALID_PAGE_PARAM);
        validateParam(page, PaginationConstants.MIN_PAGE, SearchCode.INVALID_PAGE_PARAM);

        int pageSize = parseIntOrDefault(queryParams.get("size"), PaginationConstants.DEFAULT_PAGE_SIZE, SearchCode.INVALID_PAGE_SIZE_PARAM);
        validateParam(pageSize, PaginationConstants.MIN_PAGE_SIZE, SearchCode.INVALID_PAGE_SIZE_PARAM);

        return new Pagination(page, pageSize);
    }

    private static void validateParam(int value, int minValue, ErrorCode errorCode) {
        if (value < minValue) {
            throw new ValidationException(errorCode);
        }
    }

    private static int parseIntOrDefault(String value, int defaultValue, ErrorCode code) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            throw new ValidationException(code);
        }
    }
}
