package com.ecommerce.demo.dto.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class RolesRespDto {

    private String message;
    private final Collection<? extends GrantedAuthority> roles;

    public RolesRespDto(String message, Collection<? extends GrantedAuthority> roles) {
        this.message = message;
        this.roles = roles;
    }
}
