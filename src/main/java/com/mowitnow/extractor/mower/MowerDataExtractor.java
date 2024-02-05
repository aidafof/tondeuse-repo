package com.mowitnow.extractor.mower;

import com.mowitnow.extractor.DataExtractor;
import com.mowitnow.extractor.FileContentReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MowerDataExtractor implements DataExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerDataExtractor.class);
    private final FileContentReader mowerFileReader;

    public MowerDataExtractor(FileContentReader fileReader) {
        this.mowerFileReader = fileReader;

    }

    @Override
    public List<String> extractData(String fileName) throws IOException {
        LOGGER.info("->Extract data from " + fileName);
        List<String> inputLines = mowerFileReader.readLines(fileName);
        if (inputLines.isEmpty()) {
            throw new IllegalArgumentException("input file is empty");
        }
        return inputLines;

    }

}
