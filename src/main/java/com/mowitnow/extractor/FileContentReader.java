package com.mowitnow.extractor;

import java.io.IOException;
import java.util.List;

public interface FileContentReader {
    List<String> readLines(String filename)throws IOException;
}
