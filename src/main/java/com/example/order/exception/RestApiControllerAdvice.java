package com.example.order.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(BindException exception) {

        Map<String, String> errors = new HashMap<>();

        BindingResult bindingResult = exception.getBindingResult();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {

            if(errors.containsKey(fieldError.getField())) {
                errors.put(fieldError.getField(), errors.get(fieldError.getField()) + "," + fieldError.getDefaultMessage());
                continue;
            }
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
