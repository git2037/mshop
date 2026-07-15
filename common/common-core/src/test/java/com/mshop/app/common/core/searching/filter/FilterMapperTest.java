package com.mshop.app.common.core.searching.filter;

import com.mshop.app.common.core.exception.ValidationException;
import com.mshop.app.common.core.searching.exception.SearchCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilterMapperTest {
    private static final List<FilterField> ALLOWED_SEARCH_FIELDS = List.of(
            new FilterField("email", String.class, Collections.singletonList(Operators.LIKE)),
            new FilterField("created-at", LocalDate.class, List.of(Operators.GE, Operators.LE)),
            new FilterField("deleted", Boolean.class, List.of(Operators.IS_NULL))
    );

    @Test
    void should_return_empty_list_when_no_param() {
        assertThat(FilterMapper.map(Map.of(), ALLOWED_SEARCH_FIELDS)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource(value = "invalidParams")
    void should_return_exception_when_invalid_param(Map<String, String> params, SearchCode expectedCode) {
        assertThatThrownBy(() -> FilterMapper.map(params, ALLOWED_SEARCH_FIELDS))
                .isInstanceOf(ValidationException.class)
                .satisfies(exception -> {
                    ValidationException badRequestException = (ValidationException) exception;
                    assertThat(badRequestException.getCode()).isEqualTo(expectedCode);
                });
    }

    static Stream<Arguments> invalidParams() {
        return Stream.of(
                Arguments.of(Map.of("createdAt", "abc"), SearchCode.SEARCH_FIELD_NOT_CONTAIN_UPPERCASE),
                Arguments.of(Map.of("email _like", "abc"), SearchCode.SEARCH_FIELD_NEED_TRIMMING),
                //Arguments.of(Map.of("email_ like", "abc"), IllegalArgumentException.class),
                Arguments.of(Map.of("abc", "abc"), SearchCode.INVALID_SEARCH_FIELD),
                Arguments.of(Map.of("email_ge", "up"), SearchCode.INVALID_OPERATOR),
                Arguments.of(Map.of("deleted_is_null", "abcxyz"), SearchCode.INVALID_SEARCH_VALUE),
                Arguments.of(Map.of("created-at_ge", " 2002-02-02"), SearchCode.INVALID_SEARCH_VALUE)
        );
    }

    @Test
    void should_return_list_condition_when_valid_search() {
        Map<String, String> params = Map.of("email_like", "up", "created-at_le", "2001-01-01", "deleted_is_null", "true");
        List<FilterCondition> res = FilterMapper.map(params, ALLOWED_SEARCH_FIELDS);
        assertThat(res).size().isEqualTo(3);
    }
}
