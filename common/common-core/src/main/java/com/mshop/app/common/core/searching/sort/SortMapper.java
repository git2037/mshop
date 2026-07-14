package com.mshop.app.common.core.searching.sort;

import com.mshop.app.common.core.exception.ValidationException;
import com.mshop.app.common.core.searching.exception.ExceptionBuilder;
import com.mshop.app.common.core.searching.exception.SearchCode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortMapper {
    private SortMapper() {
    }

    public static Map<String, SortDirection> map(List<String> sortParams, List<SortField> allowedSortField) {
        if (sortParams == null || sortParams.isEmpty())
            return Collections.emptyMap();

        Map<String, SortField> allowedSortFieldMap = toFieldMap(allowedSortField);
        Map<String, SortDirection> sortParamMap = new LinkedHashMap<>();

        for (String sortParam : sortParams) {
            SortField sortField = validateDirection(sortParam);
            String field = sortField.field();
            validateField(field, allowedSortFieldMap);

            sortParamMap.put(field, sortField.direction());
        }

        return sortParamMap;
    }

    private static SortField validateDirection(String sortParam) {
        String[] part = sortParam.split(",");
        int lengthPart = part.length;

        if (lengthPart > 2 || lengthPart == 0)
            throw new ValidationException(SearchCode.INVALID_SORT_PARAMETER_FORMAT, Map.of("sortParam", sortParam));

        String field = part[0];

        return lengthPart == 1
                ? new SortField(field, SortDirection.ASC)
                : new SortField(field, SortDirection.fromString(part[1]));
    }

    private static void validateField(String field, Map<String, SortField> allowedSortField) {
        if (!field.equals(field.toLowerCase()))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.SORT_FIELD_NOT_CONTAIN_UPPERCASE, field);

        if (!field.equals(field.trim()))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.SORT_FIELD_NEED_TRIMMING, field);

        if (!allowedSortField.containsKey(field))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.INVALID_SORT_FIELD, field);
    }

    private static Map<String, SortField> toFieldMap(List<SortField> allowedSortField) {
        return allowedSortField.stream()
                .collect(Collectors.toMap(
                        SortField::field,
                        field -> field
                ));
    }
}
