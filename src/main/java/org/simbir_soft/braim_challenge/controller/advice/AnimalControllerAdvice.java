package org.simbir_soft.braim_challenge.controller.advice;

import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AnimalControllerAdvice {
    @ExceptionHandler(DataInvalidException.class)
    public ResponseEntity<?> handle(DataInvalidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
