package com.ecommerce.demo.exceptions;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private String errorCode;

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
