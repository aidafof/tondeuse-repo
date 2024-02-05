package com.mowitnow.exception;


import java.io.Serial;

public class BusinessException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}
