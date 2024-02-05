package com.mowitnow.extractor;

import java.io.IOException;
import java.util.List;

public interface DataExtractor<T> {
    public List<T>extractData(String inputDataSourceName) throws IOException;
}
