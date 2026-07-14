package com.mshop.app.common.core.searching.filter;

import com.mshop.app.common.core.searching.exception.ExceptionBuilder;
import com.mshop.app.common.core.searching.exception.SearchCode;
import com.mshop.app.common.core.searching.exception.SearchConfigurationException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterMapper {
    private FilterMapper() {
    }

    private static final Map<Class<?>, Function<String, ?>> PARSERS = Map.of(
            Long.class, Long::parseLong,
            LocalDate.class, LocalDate::parse,
            Boolean.class, FilterMapper::isValidBoolean
    );

    private static boolean isValidBoolean(String value) {
        if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        }

        if (Boolean.FALSE.toString().equalsIgnoreCase(value)) {
            return Boolean.FALSE;
        }

        throw new IllegalArgumentException("Invalid boolean value: " + value);
    }

    public static List<FilterCondition> map(Map<String, String> params, List<FilterField> allowedSearchField) {
        if (params == null || params.isEmpty())
            return Collections.emptyList();

        Map<String, FilterField> fieldMap = toFieldMap(allowedSearchField);

        return params.entrySet().stream()
                .map(entry -> parse(entry.getKey(), entry.getValue()))
                .map(condition -> validate(condition, fieldMap))
                .toList();
    }

    private static Map<String, FilterField> toFieldMap(List<FilterField> allowedFields) {
        return allowedFields.stream()
                .collect(Collectors.toMap(
                        FilterField::field,
                        field -> field
                ));
    }

    private static FilterCondition parse(String key, String value) {
        String[] parts = key.split("_", 2);
        String field = parts[0];
        Operators operators = Operators.EQUAL;

        if (parts.length == 2) {
            try {
                operators = Operators.fromString(parts[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw ExceptionBuilder.invalidValueFilterParam(SearchCode.INVALID_OPERATOR, field, parts[1]);
            }
        }

        return FilterCondition.builder()
                .field(field)
                .operator(operators)
                .value(value).build();
    }

    private static FilterCondition validate(FilterCondition condition, Map<String, FilterField> allowedSearchFields) {
        String field = condition.getField();
        Operators operator = condition.getOperator();
        String value = String.valueOf(condition.getValue());
        validateField(field, allowedSearchFields);

        FilterField searchField = allowedSearchFields.get(field);
        validateOperator(searchField, operator);
        validateValue(field, operator, value);

        return FilterCondition.builder()
                .field(field)
                .operator(operator)
                .value(castValue(field, value, searchField.type()))
                .build();
    }

    private static void validateValue(String field, Operators operator, String value) {
        boolean allowedWhiteSpace = operator.equals(Operators.LIKE) || operator.equals(Operators.EQUAL);

        boolean isTrimmed = value.equals(value.trim());

        if (!allowedWhiteSpace && !isTrimmed)
            throw ExceptionBuilder.invalidValueParam(SearchCode.INVALID_SEARCH_VALUE, field, value);
    }

    private static void validateField(String field, Map<String, FilterField> allowedSearchFields) {
        if (!field.equals(field.toLowerCase()))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.SEARCH_FIELD_NOT_CONTAIN_UPPERCASE, field);

        if (!field.equals(field.trim()))
            throw ExceptionBuilder.invalidKeyParam(SearchCode.SEARCH_FIELD_NEED_TRIMMING, field);

        FilterField searchField = allowedSearchFields.get(field);
        if (searchField == null)
            throw ExceptionBuilder.invalidKeyParam(SearchCode.INVALID_SEARCH_FIELD, field);
    }

    private static void validateOperator(FilterField searchField, Operators operator) {
        if (!searchField.operators().contains(operator))
            throw ExceptionBuilder.invalidValueFilterParam(SearchCode.INVALID_OPERATOR, searchField.field(), operator.name());
    }

    private static Object castValue(String field, String value, Class<?> type) {
        if (type == String.class)
            return value;
        else if (type == null)
            throw new SearchConfigurationException(SearchCode.NULL_TYPE_SEARCH_FIELD,
                    Map.of("field", field));
        else {
            Function<String, ?> parser = PARSERS.get(type);

            if (parser != null) {
                try {
                    return parser.apply(value);
                } catch (Exception e) {
                    throw ExceptionBuilder.invalidValueParam(SearchCode.INVALID_SEARCH_VALUE, field, value);
                }
            } else {
                throw new SearchConfigurationException(SearchCode.INVALID_PARSE_TYPE,
                        Map.of("type", type.getSimpleName()));
            }
        }
    }
}
