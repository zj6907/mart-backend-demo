package com.ecommerce.demo.exceptions;

public class AuthTokenException extends SecurityException {

    public AuthTokenException(String msg) {
        super(msg);
    }
}
