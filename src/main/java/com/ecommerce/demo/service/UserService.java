package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ResponseDto;
import com.ecommerce.demo.dto.user.SigninRespDto;
import com.ecommerce.demo.dto.user.SignupDto;
import com.ecommerce.demo.exceptions.AuthTokenException;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Authtoken;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.AuthtokenRepo;
import com.ecommerce.demo.repository.UserRepo;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    UserRepo repo;
    @Autowired
    AuthtokenRepo authTokenRepo;

    public ResponseDto signup(SignupDto dto) {
        // check if user exist
        boolean exists = repo.existsByEmail(dto.getEmail());
        if (exists) throw new CustomException("User already exists!");

        // hash the password
        String encrypted = dto.getPassword();
        try {
            encrypted = hashPassword(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // save the user
        User u = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), encrypted);
        repo.save(u);

        // create and save the token
        Authtoken token = new Authtoken(u);
        authTokenRepo.save(token);

        return new ResponseDto("success", "Signup successful, user created!");
    }


    private String hashPassword(String pwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pwd.getBytes());
        byte[] bytes = md.digest();
        return DatatypeConverter.printHexBinary(bytes).toUpperCase();
    }


    public SigninRespDto signin(User u) {
        // check if user exists
        User repoUser = repo.findByEmail(u.getEmail());
        if (repoUser == null){
            throw new AuthTokenException("User doesn't exist");
        }

        // compare the password
        String encrypted = u.getPassword();
        try {
            encrypted = hashPassword(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (!encrypted.equals(repoUser.getPassword())) {
            throw new AuthTokenException("Password incorrect!");
        }

        // retrieve the token
        Authtoken authToken = authTokenRepo.findByUser(repoUser);
        if (authToken == null){
            throw new CustomException("Token doesn't exist for user " + u.getFirstName());
        }

        // response with the token
        return new SigninRespDto("Sign in successful!", authToken.getToken());
    }
}
