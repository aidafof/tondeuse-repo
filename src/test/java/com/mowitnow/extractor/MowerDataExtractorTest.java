package com.mowitnow.extractor;

import com.mowitnow.extractor.mower.MowerDataExtractor;
import com.mowitnow.extractor.mower.MowerFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


    class MowerDataExtractorTest {

    private final String inputFileName = "inputDataFileTest";


    @Test
    void mowerDataExtractor_should_extract_data() throws IOException {
        //GIVEN
        List<String> expectedDataLines = List.of("55", "123", "GAGAGAGAA");
        FileContentReader mockFileReader = mock(FileContentReader.class);
        DataExtractor mowerDataExtractor = new MowerDataExtractor(mockFileReader);

        //WHEN
        when(mockFileReader.readLines(anyString())).thenReturn(expectedDataLines);
        //THEN action
        List result = mowerDataExtractor.extractData(inputFileName);
        //ASSERT
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void mowerDataExtractor_raise_exception_when_file_not_found() throws IOException {
        //GIVEN
        FileContentReader mockFileReader = mock(FileContentReader.class);
        DataExtractor mowerDataExtractor = new MowerDataExtractor(mockFileReader);

        //THEN action
        //List<String> result = mowerDataExtractor.extractData(inputFileName);

        //THEN and ASSERT
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mowerDataExtractor.extractData(inputFileName);
        });

    }

    @Test
    void mowerDataExtractor_raise_exception_when_empty_file() throws IOException {
        //GIVEN
        FileContentReader mowerFileReader = new MowerFileReader();
        DataExtractor mowerDataExtractor = new MowerDataExtractor(mowerFileReader);
        String inputEmptyFile = "empty_mower_file.txt";

        //THEN action
        //List<String> result = mowerDataExtractor.extractData(inputFileName);

        //THHENN and ASSERT
        Assertions.assertThrows(IOException.class, () -> {
            mowerDataExtractor.extractData(inputEmptyFile);
        });
    }

}