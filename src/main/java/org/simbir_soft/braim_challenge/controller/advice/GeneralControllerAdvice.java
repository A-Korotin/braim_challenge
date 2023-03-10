package org.simbir_soft.braim_challenge.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.simbir_soft.braim_challenge.exception.AccessForbiddenException;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handle(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<?> conflict(DataConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handle(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataMissingException.class)
    public ResponseEntity<?> notFound(DataMissingException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(DataInvalidException.class)
    public ResponseEntity<?> handle(DataInvalidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<?> forbidden(AccessForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
