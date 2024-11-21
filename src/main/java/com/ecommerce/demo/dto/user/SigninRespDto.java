package com.ecommerce.demo.dto.user;

import lombok.Data;

@Data
public class SigninRespDto {

    private String message;
    private String token;

    public SigninRespDto(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
