package com.mowitnow.service;

import com.mowitnow.exception.BusinessException;
import com.mowitnow.extractor.DataExtractor;
import com.mowitnow.processor.DataProcessor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 *
 */
@AllArgsConstructor
public class LawnMowerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LawnMowerService.class);
    private final DataExtractor<String> mowerDataExtractor;
    private final DataProcessor<?> dataProcessor;

    /**
     *
     * @param inputDataName : input data source name
     * @throws IOException : exception raised when reading the file
     * @throws BusinessException : potenially logic exception
     * @return: String:  final result on the Mower process
     */
    public String execute(String inputDataName) throws IOException, BusinessException {

        LOGGER.info("-Processing LawnMower: ");

        String result = "";
        //Extract the Data from the input file

            LOGGER.info("-Input Data Extraction ");
            List<String> mowerExtractedData = mowerDataExtractor.extractData(inputDataName);
            LOGGER.info("-Starting of Application LawnMower: ");
            result = dataProcessor.process(mowerExtractedData);

        return result;
    }
}
