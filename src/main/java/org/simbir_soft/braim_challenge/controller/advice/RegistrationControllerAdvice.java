package org.simbir_soft.braim_challenge.controller.advice;

import org.simbir_soft.braim_challenge.exception.NonUniqueEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NonUniqueEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> conflict(NonUniqueEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
