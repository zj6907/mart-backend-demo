package com.ecommerce.demo.service;

import com.ecommerce.demo.exceptions.AuthTokenException;
import com.ecommerce.demo.model.Authtoken;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.AuthtokenRepo;
import com.ecommerce.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthtokenService {

    @Autowired
    AuthtokenRepo repo;
    @Autowired
    UserRepo userRepo;

    public User authenticate(String token) {
        if (token == null || token.isEmpty()) throw new AuthTokenException("Token must not be null!");
        Authtoken authToken = repo.findByToken(token);
        if (authToken == null) throw new AuthTokenException("Token not found!");
        User u = authToken.getUser();
        if (u == null) throw new AuthTokenException("Invalid token : User for this token not found!");
        return u;
    }
}
