package com.ecommerce.demo.common;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class APIResponse {
    private final boolean success;
    private final String message;

    public APIResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
