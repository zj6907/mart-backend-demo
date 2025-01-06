package com.ecommerce.demo.dto.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RefreshRespDto extends TokensRespDto {

    public RefreshRespDto(String message, Collection<? extends GrantedAuthority> roles, String accessToken, String refreshToken) {
        super(message, roles, accessToken, refreshToken);
    }
}
