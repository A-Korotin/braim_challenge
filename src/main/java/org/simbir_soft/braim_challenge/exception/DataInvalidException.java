package org.simbir_soft.braim_challenge.exception;

public class DataInvalidException extends RuntimeException {
    public DataInvalidException() {
    }

    public DataInvalidException(String message) {
        super(message);
    }
}
