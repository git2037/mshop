package com.mshop.app.common.core.searching.parser;

import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.ValidationException;
import com.mshop.app.common.core.searching.exception.SearchCode;
import com.mshop.app.common.core.searching.model.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaginationPaserTest {

    @ParameterizedTest
    @MethodSource(value = "invalidParams")
    void should_throw_exception_when_invalid_exception(Map<String, String> map, ErrorCode expectedCode) {
        assertThatThrownBy(() -> PaginationPaser.parse(map))
                .isInstanceOf(ValidationException.class)
                .satisfies(e -> {
                    ValidationException validationException = (ValidationException) e;
                    assertThat(validationException.getCode()).isEqualTo(expectedCode);
                });
    }

    static Stream<Arguments> invalidParams() {
        return Stream.of(
                Arguments.of(Map.of("page", "abc"), SearchCode.INVALID_PAGE_PARAM),
                Arguments.of(Map.of("page", "0"), SearchCode.INVALID_PAGE_PARAM)
        );
    }

    @Test
    void should_return_pagination_when_valid_param() {
        Map<String, String> params = Map.of("page", "1", "size", "10");
        Pagination pagination = new Pagination(1, 10);
        Pagination result = PaginationPaser.parse(params);
        assertThat(result.getPage()).isEqualTo(pagination.getPage());
        assertThat(result.getPageSize()).isEqualTo(pagination.getPageSize());
    }
}