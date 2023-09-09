package com.example.springproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Order(1)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/**").permitAll() // Allow access without authentication)
                        .requestMatchers("/register").permitAll() // Allow access without authentication)
                        .requestMatchers("/login").permitAll() // Allow access without authentication)
                        .requestMatchers("/seller/**").hasRole("SELLER") // Seller role access
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")   // Buyer role access
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
