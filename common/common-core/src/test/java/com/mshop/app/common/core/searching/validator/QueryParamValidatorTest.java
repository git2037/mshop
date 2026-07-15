package com.mshop.app.common.core.searching.validator;

import com.mshop.app.common.core.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class QueryParamValidatorTest {

    @ParameterizedTest
    @MethodSource(value = "invalidParams")
    void should_return_exception_when_invalid_field(Map<String, String> params, String expectedMessage) {
        assertThatThrownBy(() -> QueryParamValidator.validateParams(params))
                .isInstanceOf(ValidationException.class)
                .hasMessage(expectedMessage);
    }

    static Stream<Arguments> invalidParams() {
        return Stream.of(
                Arguments.of(Map.of("email ", "abc"), "The query parameter 'email ' is invalid."),
                Arguments.of(Map.of("Email", "abc"), "The query parameter 'Email' is invalid."),
                Arguments.of(Map.of("abcd", "   "), "The query value '   ' is invalid for query parameter 'abcd'.")
        );
    }

    @Test
    void should_return_map_when_valid_field() {
        Map<String, String> validParams = Map.of("email", "abc",
                "abc", "abc");

        assertThat(QueryParamValidator.validateParams(validParams))
                .isEqualTo(validParams);
    }
}
