package com.mshop.app.common.core.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @ParameterizedTest
    @MethodSource(value = "input")
    void kebabCaseToCamelCase(String input, String expected) {
        String actual = StringUtils.kebabCaseToCamelCase(input);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> input() {
        return Stream.of(Arguments.of("created-at", "createdAt"),
                Arguments.of("email", "email"));
    }
}