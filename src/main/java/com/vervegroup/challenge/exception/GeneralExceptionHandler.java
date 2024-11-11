package com.vervegroup.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleMissingParams(Exception ex) {
        return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
    }
}
