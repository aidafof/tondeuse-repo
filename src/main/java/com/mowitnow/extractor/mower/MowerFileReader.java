package com.mowitnow.extractor.mower;

import com.mowitnow.extractor.FileContentReader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 */
public class MowerFileReader implements FileContentReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MowerFileReader.class);

    @Override
    public List<String> readLines(String fileName) throws IOException {

        var resourceFile = getResource(fileName);
        var msg = "";
        //check ressource URL
        if (resourceFile == null) {
            msg = "-> input file:%s not found in resources directory";
            LOGGER.error(msg.formatted(fileName));
            throw new FileNotFoundException(msg.formatted(fileName));
        }
        var file = new File(resourceFile.getFile());
        // check file path
        if (file.getPath() == null) {
            msg = "-> Path file is not accessible :%";
            LOGGER.error(msg.formatted(fileName));
            throw new FileNotFoundException(msg.formatted(fileName));
        }
        //check file content
        if (file.length() == 0) {
            LOGGER.error("-> Error : input file is empty :%s".formatted(fileName));
            throw new IOException("input file is empty: %s".formatted(fileName));
        }
        return FileUtils.readLines(file, StandardCharsets.UTF_8);
    }

    /**
     * return the resource file URL
     *
     * @param fileName : file name
     * @return URL : file Url
     */
    protected URL getResource(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }


}
