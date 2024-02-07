package com.mowitnow.processor;

import com.mowitnow.exception.BusinessException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileDataProcessorTest {

    private final String COMMA_SEPARATOR = ",";
    private final FileDataProcessor fileDataProcessor = new FileDataProcessor();

    @ParameterizedTest(name = "valid file: [{0}]")
    @CsvSource({
            "'5 5, 1 2 N,GAGAGAGAA,3 3 E,AADAADADDA', '1 3 N,5 1 E'",
            "'5 5, 1 2 N,GAGAGAGAA,3 3 E,AADAADADDA', '1 3 N,5 1 E'"
    })
    void process_ParameterizedTest_when_valid_input_file(String inputLines, String outputLines) throws BusinessException {

        //GIVEN
        String expectedResult = outputLines.replace(COMMA_SEPARATOR, System.lineSeparator());

        //conversion of input lines into a list of strings
        List<String> fileLines = Arrays.stream(inputLines.split(COMMA_SEPARATOR))
                .map(String::trim)
                .toList();

        //WHEN
        String result = fileDataProcessor.process(fileLines);

        //ASSERT
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest(name = "input test line: [{0}]")
    @CsvSource({
            "'5 5, 1 2 N,GAGAGAGAA,3 3 E,AADAADADDA', '1 3 N,5 1 E'"
    })
    void process_ParameterizedTest_when_invalid_input_file(String inputLines, String outputLines) throws BusinessException {
        //GIVEN

        String expectedResult = outputLines.replace(COMMA_SEPARATOR, System.lineSeparator());

        //inputLines conversion a list of strings
        List<String> fileLines = Arrays.stream(inputLines.split(COMMA_SEPARATOR))
                .map(String::trim)
                .toList();
        //WHEN
        String result = fileDataProcessor.process(fileLines);
        //ASSERT
        assertEquals(expectedResult, result);
    }

}