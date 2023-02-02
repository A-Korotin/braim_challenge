package org.simbir_soft.braim_challenge.exception;

public class NonUniqueEmailException extends RuntimeException {
    public NonUniqueEmailException() {

    }

    public NonUniqueEmailException(String message) {
        super(message);
    }
}
