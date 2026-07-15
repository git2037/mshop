package com.mshop.app.common.core.searching.sort;

import com.mshop.app.common.core.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SortMapperTest {

    private final List<SortField> allowedSortField = List.of(
            new SortField("created-at"),
            new SortField("created-by"));

    @Test
    void should_return_empty_map_when_param_empty() {
        List<String> params = List.of();

        assertThat(SortMapper.map(params, allowedSortField)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource(value = "invalidSortParams")
    void should_throw_exception_when_invalid_field(List<String> params, String expectedMessage) {
        assertThatThrownBy(() -> SortMapper.map(params, allowedSortField))
                .isInstanceOf(ValidationException.class)
                .hasMessage(expectedMessage);
    }

    static Stream<Arguments> invalidSortParams() {
        return Stream.of(
                Arguments.of(List.of("created-at   ,asc"), "The sort parameter 'created-at   ' is invalid."),
                Arguments.of(List.of("abc,asc"), "The field 'abc' is not allowed for sorting."),
                Arguments.of(List.of("Created-at,asc"), "The sort parameter 'Created-at' is invalid."),
                Arguments.of(List.of(","), "Invalid sort parameter format: ','."),
                Arguments.of(List.of("created-at,asc,desc"), "Invalid sort parameter format: 'created-at,asc,desc'.")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "invalidDirections")
    void should_throw_exception_when_param_has_invalid_direction(List<String> params) {
        assertThatThrownBy(() -> SortMapper.map(params, allowedSortField))
                .isInstanceOf(ValidationException.class);
    }

    static Stream<List<String>> invalidDirections() {
        return Stream.of(
                List.of("created-at,abc"),
                List.of("created-at,  ge")
        );
    }

    @Test
    void should_return_map_when_valid_param() {
        List<String> params = List.of("created-at", "created-by,desc");
        Map<String, SortDirection> map = SortMapper.map(params, allowedSortField);

        assertThat(map).containsExactly(
                entry("created-at", SortDirection.ASC),
                entry("created-by", SortDirection.DESC)
        );
    }
}
