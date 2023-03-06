package org.simbir_soft.braim_challenge.exception;

public class DataMissingException extends RuntimeException {
    public DataMissingException() {
    }

    public DataMissingException(String message) {
        super(message);
    }
}
