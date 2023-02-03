package org.simbir_soft.braim_challenge.controller.advice;

import org.simbir_soft.braim_challenge.exception.AccessForbiddenException;
import org.simbir_soft.braim_challenge.exception.InvalidAccountIdException;
import org.simbir_soft.braim_challenge.exception.NonUniqueEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NonUniqueEmailException.class)
    public ResponseEntity<?> conflict(NonUniqueEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidAccountIdException.class)
    public ResponseEntity<?> notFound(InvalidAccountIdException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<?> forbidden(AccessForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
