package com.ecommerce.demo.exceptions;

public class AuthTokenException extends CustomException {

    public AuthTokenException(String msg, String errorCode) {
        super(msg, errorCode);
    }
}
