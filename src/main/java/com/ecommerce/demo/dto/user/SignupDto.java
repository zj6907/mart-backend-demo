package com.ecommerce.demo.dto.user;

import lombok.Data;

@Data
public class SignupDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
