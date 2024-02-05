package com.mowitnow.extractor.mower;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MowerFileReaderTest {


    @Test
    void mowerFileReader_should_read_when_valid_File_provided() throws Exception {

        //GIVEN
        String testFile = "valid_mower_file.txt";
        String testLine = "GAGAGAGAA";
        MowerFileReader mowerFileReader = spy(new MowerFileReader());
        URL mockURL = new File("src/test/resources/" + testFile).toURI().toURL();
        when((mowerFileReader).getResource(testFile)).thenReturn(mockURL);

        //WHEN
        List<String> expectedLines = mowerFileReader.readLines(testFile);
        //ASSERT
        assertEquals(5, expectedLines.size());
        Assertions.assertTrue(expectedLines.stream()
                .anyMatch(s -> s.equalsIgnoreCase(testLine)));
    }

    @Test
    void mowerFileReader_should_throw_IOException_when_FileIsEmpty() throws IOException {
        //GIVEN
        String testFile = "empty_mower_file.txt";
        MowerFileReader mowerFileReader = spy(new MowerFileReader());
        URL mockURL = new File("src/test/resources/" + testFile).toURI().toURL();
        doReturn(mockURL).when(mowerFileReader).getResource(testFile);

        //WHEN and ASSERT
        assertThrows(IOException.class, () -> mowerFileReader.readLines(testFile));
    }

    @Test
    void mowerFileReader_should_Throw_FileNotFound_whenFileNotFound() throws Exception {
        //GIVEN
        String testFile = "non_existing_file.txt";
        MowerFileReader mowerFileReader = spy(new MowerFileReader());
        when((mowerFileReader).getResource(testFile)).thenReturn(null);

        //WHEN and ASSERT
        assertThrows(FileNotFoundException.class, () ->
                mowerFileReader.readLines(testFile));
    }


}