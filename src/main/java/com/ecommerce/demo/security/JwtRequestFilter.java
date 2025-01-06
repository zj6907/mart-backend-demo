package com.ecommerce.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailService userDetailsService;
    @Autowired
    JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessTk = getTokenFromHeader(request);
        if (StringUtils.hasText(accessTk)) {
            // Extract user information from the token
            Claims claims;
            try {
                claims = jwtProvider.getPayloadFromJwt(accessTk);
            } catch (Exception e) {
                handleException(e, response);
                return;
            }
            if (claims != null) {
                // if token is valid, add authorities to the context
                AbstractAuthenticationToken authToken;
                UserDetails userDetails;
                if (isSensitiveAction(request)) {
                    // For sensitive actions, use authorities from DB. In case of blacklist, account disabled, ...
                    userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
                    authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                } else {
                    // For nonsensitive actions, just use authorities from the token
                    userDetails = new User(claims.getSubject(), "", jwtProvider.getAuthoritiesFromPayload(claims));
                    authToken = new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                }
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String json = null;
        if (e instanceof ExpiredJwtException)
            json = "{\"error\": \"Unauthorized\",\"errorCode\": \"ACCESS_EXPIRED\", \"message\": \"The access token has expired.\"}";
        else if (e instanceof Exception) json = e.getMessage();
        response.getWriter().write(json);
    }

    String[] sensitiveURIs = {"/category/(create|update|delete)", "/product/(add|update|delete)"};
    String regex = Arrays.stream(sensitiveURIs).map(r -> "^" + r).collect(Collectors.joining("|"));

    private boolean isSensitiveAction(HttpServletRequest request) {
        return Pattern.compile(this.regex).matcher(request.getRequestURI()).find();
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        // System.out.println("<< getTokenFromRequest: " + request.getRequestURI() + " >>");
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
