package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.ResponseDto;
import com.ecommerce.demo.dto.user.*;
import com.ecommerce.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto dto) {
        return service.signup(dto);
    }

    @PostMapping("/login")
    public LoginRespDto login(@RequestBody LoginDto dto) {
        return service.login(dto);
    }

    @PostMapping("/refresh")
    public RefreshRespDto refresh(@RequestBody TokensDto dto) {
        return service.refresh(dto.getRefreshToken());
    }

    @PostMapping("/parse")
    public @ResponseBody RolesRespDto parse(@RequestBody TokensDto dto) {
        return service.parse(dto.getAccessToken());
    }

}
