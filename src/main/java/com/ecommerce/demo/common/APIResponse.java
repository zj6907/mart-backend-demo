package com.ecommerce.demo.common;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class APIResponse {
    private final boolean success;
    private final String message;
    private String errorCode;

    public APIResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public APIResponse(boolean success, String message, String errorCode) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
