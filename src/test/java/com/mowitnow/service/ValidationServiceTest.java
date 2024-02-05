package com.mowitnow.service;

import com.mowitnow.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationServiceTest {
    private final ValidationService validationService = new ValidationService();

    @Test
    void validateLawnData_Should_Not_Throw_Exception_when_valid_data_inInput() {
        assertDoesNotThrow(() -> validationService.validateLawnData("5 5"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"5", "5 5 5", "x y", ""})
    void validateLawnData_Should_Throw_Exception_when_input_invalid_data_inInput(String lawnData) {
        assertThrows(BusinessException.class, () -> validationService.validateLawnData(lawnData));
    }
    @Test
    void validateMowerData_Should_NotThrow_Exception_when_valid_data_inInput() {
        List<String> mowerData = List.of("1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
        assertDoesNotThrow(() -> validationService.validateMowerData(mowerData));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMowerData")
    void validateMowerData_Should_Throw_Exception_when_invalid_data_inIput(List<String> mowerData) {
        assertThrows(BusinessException.class, () -> validationService.validateMowerData(mowerData));
    }

    private static Stream<Arguments> provideInvalidMowerData() {
        return Stream.of(
                Arguments.of(List.of("1 2 X", "GAGAGAGAA")),  // Invalid orientation
                Arguments.of(List.of("1 2 N", "XYZ")),        // Invalid instructions
                Arguments.of(List.of("1 N", "GAGA"))          // Invalid position
        );
    }
}