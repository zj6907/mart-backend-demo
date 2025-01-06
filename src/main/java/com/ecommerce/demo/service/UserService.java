package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ResponseDto;
import com.ecommerce.demo.dto.user.*;
import com.ecommerce.demo.exceptions.AuthTokenException;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Role;
import com.ecommerce.demo.model.UserEntity;
import com.ecommerce.demo.repository.AuthtokenRepo;
import com.ecommerce.demo.repository.RoleRepo;
import com.ecommerce.demo.repository.UserRepo;
import com.ecommerce.demo.security.JwtProvider;
import com.ecommerce.demo.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepo repo;
    @Autowired
    AuthtokenRepo authTokenRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    @Transactional
    public ResponseDto signup(SignupDto dto) {
        // check if user exist
        boolean exists = repo.existsByEmail(dto.getEmail());
        if (exists) throw new CustomException("User already exists!");
        // hash the password
        String encoded = encode(dto.getPassword());
        UserEntity user = new UserEntity(dto.getFirstName(), dto.getLastName(), dto.getEmail(), encoded);
        // default role
        Role role = roleRepo.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        // save the user
        repo.save(user);
        return new ResponseDto("success", "Signup successful, user created! Please Login!");
    }

    private String encode(String pwd) {
        return passwordEncoder.encode(pwd);
        /*try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte[] bytes = md.digest();
            return DatatypeConverter.printHexBinary(bytes).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }


    public LoginRespDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String message = "Sign in successful!";
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        return new LoginRespDto(message, roles, jwtProvider.generateAccessJwt(authentication), jwtProvider.generateRefreshJwt(authentication));
    }

    public UserEntity findByEmail(String email) {
        UserEntity byEmail = repo.findByEmail(email);
        if (byEmail == null) throw new CustomException("User not found!");
        return byEmail;
    }

    public RefreshRespDto refresh(String refreshTk) {
        if (refreshTk == null || refreshTk.isEmpty()) throw new CustomException("Refresh Token cann't be empty");
        Claims claims = null;
        try {
            claims = jwtProvider.getPayloadFromJwt(refreshTk);
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException)
                throw new AuthTokenException("Refresh Token Expired!", "REFRESH_EXPIRED");
        }
        String subject = jwtProvider.getSubjectFromPayload(claims);
        Collection<? extends GrantedAuthority> authorities = jwtProvider.getAuthoritiesFromPayload(claims);
        String accessJwt = jwtProvider.generateAccessJwt(subject, authorities);
        // If refresh token has less than half of its lifespan, generate it.
        String refreshJwt = null;
        long expirationTime = claims.getExpiration().getTime();
        long currentTime = new Date().getTime();
        if (currentTime > expirationTime - SecurityConstants.REFRESH_LIFESPAN_IN_MS / 2) {
            refreshJwt = jwtProvider.generateRefreshJwt(subject, authorities);
        }
        String message = "Refres Token Successful!";
        return new RefreshRespDto(message, authorities, accessJwt, refreshJwt);
    }

    public RolesRespDto parse(String accessToken) {
        Claims claims = null;
        try {
            claims = jwtProvider.getPayloadFromJwt(accessToken);
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException)
                throw new AuthTokenException("Access Token Expired!", "ACCESS_EXPIRED");
            throw e;
        }
        return new RolesRespDto("Token parse successful", jwtProvider.getAuthoritiesFromPayload(claims));
    }
}
