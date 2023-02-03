package org.simbir_soft.braim_challenge.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.simbir_soft.braim_challenge.exception.AccessForbiddenException;
import org.simbir_soft.braim_challenge.exception.InvalidAccountIdException;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<?> conflict(DataConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException e) {
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
