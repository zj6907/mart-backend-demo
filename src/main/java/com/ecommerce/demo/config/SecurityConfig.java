package com.ecommerce.demo.config;

import com.ecommerce.demo.security.JwtAuthEntryPoint;
import com.ecommerce.demo.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/*
 * Authentication Filter  -->
 * */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Enable @PreAuthorize
public class SecurityConfig {

    @Autowired
    JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig) throws Exception {
        http
                .cors(withDefaults()) // enable CORS
                .csrf(AbstractHttpConfigurer::disable) // To disable CSRF protection (not recommended for production).
                .authorizeHttpRequests((ahr) -> ahr
                        .requestMatchers("/user/**").permitAll() // Register and Login and Refresh should be permitted for all.
                        .requestMatchers("/product/findAll", "/category/findAll").permitAll() // Anyone could visit the products.
                        .requestMatchers("/image/proxy*").permitAll() // Allow the image proxy
                        .requestMatchers("/swagger-ui/**", "/edemo-api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headersConfig -> {
                    headersConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin); // Allow H2 to be displayed in a frame
                })
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(jwtAuthEntryPoint) // Customize error action. Spring Security's default error action is showing a log in page.
                )
                .sessionManagement(config -> config
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults())
        ;
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

