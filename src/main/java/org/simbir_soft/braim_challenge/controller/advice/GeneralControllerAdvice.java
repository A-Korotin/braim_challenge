package org.simbir_soft.braim_challenge.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.simbir_soft.braim_challenge.exception.AccessForbiddenException;
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
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handle(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<?> conflict(DataConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handle(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataMissingException.class)
    public ResponseEntity<?> notFound(DataMissingException e) {
        e.printStackTrace();
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<?> forbidden(AccessForbiddenException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
