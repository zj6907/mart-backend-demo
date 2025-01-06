package com.ecommerce.demo.dto.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class TokensRespDto extends RolesRespDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer ";

    public TokensRespDto(String message, Collection<? extends GrantedAuthority> roles, String accessToken, String refreshToken) {
        super(message, roles);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
