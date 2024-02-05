package com.mowitnow;

import com.mowitnow.exception.BusinessException;
import com.mowitnow.exception.ErrorCode;
import com.mowitnow.extractor.DataExtractor;
import com.mowitnow.extractor.FileContentReader;
import com.mowitnow.extractor.mower.MowerDataExtractor;
import com.mowitnow.extractor.mower.MowerFileReader;
import com.mowitnow.processor.FileDataProcessor;
import com.mowitnow.service.LawnMowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * bootstrap processing of an input file containing data and instructions to monitor a set of mowers
 *
 */
public class AppMowerLauncher
{
   private static final Logger LOGGER = LoggerFactory.getLogger(AppMowerLauncher.class);

    private final LawnMowerService lawnMowerService;

    // Constructor for injecting dependencies
    public AppMowerLauncher(LawnMowerService lawnMowerService) {
        this.lawnMowerService = lawnMowerService;
    }

    /**
     * Launch the input file for processing by a speialized service
     * @param fileName
     * @return String the result to report final position of the lmower on the lawn area
     * @throws IOException
     * @throws BusinessException
     */
    public String launch(String fileName) throws IOException, BusinessException {
        LOGGER.info("-> Starting Application LawnMower");
        String processResult;
        System.out.println("- Starting Application LawnMower");//to remove

        if (fileName == null || fileName.isEmpty()) {
            LOGGER.error("Error with file provided: %s ".formatted(fileName));
            throw new IllegalArgumentException("argument file name is missing");
        }
        try {
            // Execute the landMower service and return process result
            processResult = lawnMowerService.execute(fileName);
            LOGGER.info("** End of Lawn Mower application - final position: %s".formatted(processResult));

        } catch(IOException e) {
            throw new BusinessException((ErrorCode.IOException_Error.getLabel()).formatted(e.getMessage()));
        }
        return processResult;
    }

   public static void main( String ...args ) throws IOException, BusinessException
    {
        LOGGER.info("->Starting Application LawnMower");
        System.out.println("-Starting Application LawnMower");
        if (args.length != 1){
            throw new IllegalArgumentException("argument file name is missing");
        }
        //instanciation of dependencies classes
        FileContentReader mowerFileReader = new MowerFileReader();
        DataExtractor<String> mowerDataExtractor =new MowerDataExtractor(mowerFileReader);

        LawnMowerService lawnMowerService = new LawnMowerService(mowerDataExtractor, new FileDataProcessor());
        AppMowerLauncher launcher = new AppMowerLauncher(lawnMowerService);
        launcher.launch(args[0]);

    }
}
