package com.ecommerce.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CustomUserDetailService userDetailsService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken() { // @RequestBody RefreshTokenRequest refreshTokenRequest
        String refreshToken = ""; // String refreshToken = refreshTokenRequest.getRefreshToken();
        // Validate the refresh token
        if (jwtProvider.getPayloadFromJwt(refreshToken) != null) {
            String username = jwtProvider.getSubjectFromPayload(jwtProvider.getPayloadFromJwt(refreshToken));
            String newAccessToken = jwtProvider.generateAccessJwt(null); // todo
            return ResponseEntity.ok(new RefreshTokenResponse(newAccessToken, refreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
    }


    private class RefreshTokenResponse {
        private final String newAccessToken;
        private final String refreshToken;

        public RefreshTokenResponse(String newAccessToken, String refreshToken) {
            this.newAccessToken = newAccessToken;
            this.refreshToken = refreshToken;
        }
    }
}
