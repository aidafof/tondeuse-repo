package com.mowitnow;


import com.mowitnow.exception.BusinessException;
import com.mowitnow.extractor.DataExtractor;
import com.mowitnow.extractor.FileContentReader;
import com.mowitnow.extractor.mower.MowerDataExtractor;
import com.mowitnow.extractor.mower.MowerFileReader;
import com.mowitnow.processor.FileDataProcessor;
import com.mowitnow.service.LawnMowerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit class test for lawnMower application entry point
 * dependencies behavior are mocked
 */
@ExtendWith(MockitoExtension.class)
class AppMowerLauncherTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppMowerLauncherTest.class);

    @Mock
    private LawnMowerService lawnMowerService;


    @Test
    void appLauncher_should_execute_when_Valid_file_inInput() throws IOException, BusinessException {

        //GIVEN
        String fileName = "valid_mower_file.txt";
        // List<String> fileLines = List.of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");
        String expectedOutput = "1 3 N,5 1 E".replace(",", System.lineSeparator());
        MockitoAnnotations.openMocks(this);

        when(lawnMowerService.execute(fileName)).thenReturn(expectedOutput);
        AppMowerLauncher launcher = new AppMowerLauncher(lawnMowerService);
        // WHEN
        String processResult = launcher.launch(fileName);
        //THEN

        verify(lawnMowerService, times(1)).execute(fileName);
        assertEquals(expectedOutput, processResult);
    }

    @Test
    void appLauncher_should_throw_Exception_When_No_Argument_inInput() throws IOException, BusinessException {
        //GIVEN
        String[] args = {};
        //WHEN main call, THEN ASSERT
        assertThrows(IllegalArgumentException.class, () -> AppMowerLauncher.main(args));
    }

    @ParameterizedTest()
    @CsvSource({
            "valid_mower_file.txt, '1 3 N,5 1 E', ' '  ",
            "2lines_files.txt, BusinessExceptionExpected, IOException",
            "missing_lawn_lines.txt,BusinessExceptionExpected, Invalid coordinates Type",
            "invalid_line3_file.txt, BusinessExceptionExpected, Invalid instruction",
            "invalid_line2_file.txt, BusinessExceptionExpected, Invalid position/orientation line format",
            "missing_mower_lines.txt, BusinessExceptionExpected, Inconsistent line count-1"
    })
    void appLauncher_ParameterizedTest_input_file(String fileName, String expectedResult, String expectedMessage) {
        FileContentReader mowerFileReader = new MowerFileReader();
        DataExtractor<String> mowerDataExtractor = new MowerDataExtractor(mowerFileReader);
        lawnMowerService = new LawnMowerService(mowerDataExtractor, new FileDataProcessor());

        AppMowerLauncher launcher = new AppMowerLauncher(lawnMowerService);

        if (expectedResult.equals("BusinessExceptionExpected")) {

            BusinessException thrown = assertThrows(BusinessException.class, () -> launcher.launch(fileName));
            LOGGER.info(thrown.getMessage());
            assertEquals(BusinessException.class, thrown.getClass());
            if (!expectedMessage.isEmpty()) {
                Assertions.assertTrue(thrown.getMessage().contains(expectedMessage));
            }
        } else {
            try {
                String result = launcher.launch(fileName);
                String expectedResFormatted = expectedResult.replace(",", System.lineSeparator());
                assertEquals(expectedResFormatted, result);
            } catch (Exception e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
}
