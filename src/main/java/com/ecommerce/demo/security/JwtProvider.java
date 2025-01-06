package com.ecommerce.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JwtProvider {

    public static void main(String[] args) {
//        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET_KEY);  // Generate HMAC key from secret
//        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN"));
//        Date currentTime = new Date();
//        String jwt = Jwts.builder()
//                .subject("100com")
//                .claim("roles", authorities)
//                .issuedAt(new Date())
//                .issuer("ecommerce")
//                .expiration(new Date(currentTime.getTime() + 60000))
//                .signWith(key, Jwts.SIG.HS256) // algorithm
//                .compact();
//        System.out.println(jwt);
//
        JwtProvider inst = new JwtProvider();
        String accessTk = inst.generateAccessJwt("wang", new ArrayList<>() {{
            add(new SimpleGrantedAuthority("ADMIN"));
        }});
    }

    public String generateAccessJwt(Authentication auth) {
        return generate(auth, SecurityConstants.ACCESS_LIFESPAN_IN_MS); // 86400000 = 1 day,  3600000 = 1 hour
    }

    public String generateAccessJwt(String username, Collection<? extends GrantedAuthority> authorities) {
        return generate(username, authorities, SecurityConstants.ACCESS_LIFESPAN_IN_MS); // 86400000 = 1 day,  3600000 = 1 hour
    }

    public String generateRefreshJwt(Authentication auth) {
        return generate(auth, SecurityConstants.REFRESH_LIFESPAN_IN_MS); // 7 days expiration
    }

    public String generateRefreshJwt(String username, Collection<? extends GrantedAuthority> authorities) {
        return generate(username, authorities, SecurityConstants.REFRESH_LIFESPAN_IN_MS); // 7 days expiration
    }

    public String generate(Authentication authentication, long lifespanInMs) {
        return generate(authentication.getName(), authentication.getAuthorities(), lifespanInMs);
    }

    public String generate(String username, Collection<? extends GrantedAuthority> authorities, long lifespanInMs) {
        Date currentTime = new Date();
        Date expirationDate = new Date(currentTime.getTime() + lifespanInMs);
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET_KEY);  // Generate HMAC key from secret
        return Jwts.builder()
                .subject(username)
                .claim("roles", authorities) // List
                .issuedAt(currentTime)
                .issuer("ecommerce")
                .expiration(expirationDate)
                .signWith(key, Jwts.SIG.HS256) // algorithm
                .compact();
    }

    public String getSubjectFromPayload(Claims claims) {
        return claims.getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthoritiesFromPayload(Claims claims) {
        if (claims == null) return Collections.emptyList();
        Collection<Map<String, String>> roles = (Collection) claims.get("roles"); // ArrayList<LinkedHashMap<"authority",v>>
        return roles.stream().map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toList());
    }


    /**
     * Parse the JWT to validate signature, expiration, etc.
     * Set the signing key to validate the JWT signature
     * This will throw an exception if the JWT is invalid
     * If no exception, the token is valid
     */
    public Claims getPayloadFromJwt(String token) {
        if (!StringUtils.hasText(token)) {
            System.out.println("Token not exist in request header");
            return null;
        }
        try {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET_KEY);  // Generate HMAC signing key from secret
            return Jwts.parser()
                    .verifyWith(key)  // Set the signing key to validate the JWT signature
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            // this is probably due to malicious attack
            System.out.println("Invalid signature");
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired");
            // if refreshTk is valid, create a new accessTk
            throw e;
            // if refreshTk is invalid, force client to login
        } catch (MalformedJwtException e) {
            // this is probably due to malicious attack
            System.out.println("Invalid token format");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");
        } catch (Exception e) {
            System.out.println("An error occurred during token validation");
        }
        return null;
    }

}
