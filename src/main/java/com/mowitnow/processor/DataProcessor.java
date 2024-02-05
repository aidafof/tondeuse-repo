package com.mowitnow.processor;

import com.mowitnow.exception.BusinessException;

import java.util.List;

public interface DataProcessor<T>{
        String process (List<?> data) throws BusinessException ;
}
