package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.ResponseDto;
import com.ecommerce.demo.dto.user.SigninRespDto;
import com.ecommerce.demo.dto.user.SignupDto;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/signup")
    public ResponseDto signup(SignupDto dto) {
        return service.signup(dto);
    }

    @PostMapping("/signin")
    public SigninRespDto signin(User u){
        return service.signin(u);
    }

}
