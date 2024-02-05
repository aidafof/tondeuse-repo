package com.mowitnow.service;

import com.mowitnow.exception.BusinessException;
import com.mowitnow.extractor.DataExtractor;
import com.mowitnow.processor.DataProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LawnMowerServiceTest {

    // Mock dependencies
    DataExtractor mockMowerDataExtractor = mock(DataExtractor.class);
    DataProcessor mockDataProcessor = mock(DataProcessor.class);
    LawnMowerService lawnMowerService = new LawnMowerService(mockMowerDataExtractor, mockDataProcessor);


    @Test
    void Execute_should_process_when_valid_File_provided() throws IOException, BusinessException {

        //GIVEN - setup data
        String validFile = "valid_mower_file.txt";
        List<String> mowerExtractedData = List.of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA", "1 3 N", "5 1 E");
        String outputLines = "1 3 N\n5 1 E";

        // Configure mocks to return specific values when methods are called
        when(mockMowerDataExtractor.extractData(validFile)).thenReturn(mowerExtractedData);
        when(mockDataProcessor.process(mowerExtractedData)).thenReturn(outputLines);

        // WHEN (execute is called)
        String processResult = lawnMowerService.execute(validFile);

        //THEN Assertions
        assertEquals(outputLines, processResult, "The processed result should match expected output");

        // Verify that mocked methods are called
        verify(mockMowerDataExtractor, times(1)).extractData(validFile);
        verify(mockDataProcessor, times(1)).process(mowerExtractedData);
    }

    @Test
    void execute_should_Throw_Exception_when_Invalid_File_provided() throws IOException, BusinessException {
        // GIVEN
        String invalidFile = "invalid_line3_file.txt";
        List<String> extractedDataWithInvalidCommands = List.of("5 5", "1 2 N", "GAXXGAGAA", "3 3 E", "AADAADADDA", "1 3 N", "5 1 E");
        // Configure mocks instances
        when(mockMowerDataExtractor.extractData(invalidFile)).thenReturn(extractedDataWithInvalidCommands);
        // Assume processing invalid commands results in a BusinessException
        when(mockDataProcessor.process(extractedDataWithInvalidCommands)).thenThrow(BusinessException.class);

        // WHEN & THEN (execute is called and we expect a BusinessException)
        assertThrows(BusinessException.class, () -> {
            lawnMowerService.execute(invalidFile);
        }, "A BusinessException should be thrown for invalid data");

        // Verify that the extractor was called but the processor threw an exception
        verify(mockMowerDataExtractor, times(1)).extractData(invalidFile);
        verify(mockDataProcessor, times(1)).process(extractedDataWithInvalidCommands);
    }
}