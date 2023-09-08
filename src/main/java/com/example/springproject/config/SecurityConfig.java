package com.example.springproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/seller/**").hasRole("SELLER") // Seller role access
                                .requestMatchers("/admin/**").hasRole("ADMIN")   // Admin role access
                                .requestMatchers("/buyer/**").hasRole("BUYER")   // Buyer role access
                                .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer ->
                        loginConfigurer
                                .loginPage("/login")
                                .permitAll()
                )
                .httpBasic(httpBasicConfigurer ->
                        httpBasicConfigurer.realmName("My Realm")
                )
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails seller = User
                .withUsername("seller")
                .password("{noop}sellerpass")
                .roles("SELLER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();

        UserDetails buyer = User
                .withUsername("buyer")
                .password("{noop}buypass")
                .roles("BUYER")
                .build();

        return new InMemoryUserDetailsManager(seller, admin, buyer);
    }
}
