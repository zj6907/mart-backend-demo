package com.ecommerce.demo.dto.user;

import lombok.Data;

@Data
public class TokensDto {

    private String accessToken;
    private String refreshToken;

}
