package com.ecommerce.demo.exceptions;

import com.ecommerce.demo.common.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<APIResponse> handleCustomException(CustomException e) {
        return new ResponseEntity<>(new APIResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthTokenException.class)
    public final ResponseEntity<APIResponse> handleAuthTokenException(AuthTokenException e) {
        return new ResponseEntity<>(new APIResponse(false, e.getMessage(), e.getErrorCode()), HttpStatus.UNAUTHORIZED);
    }

}
