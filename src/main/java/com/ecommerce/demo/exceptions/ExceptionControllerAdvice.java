package com.ecommerce.demo.exceptions;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthTokenException.class)
    public final HttpEntity<String> handleAuthTokenException(AuthTokenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

}
