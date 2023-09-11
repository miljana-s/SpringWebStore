package com.example.springproject.config;

import com.example.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http/*.csrf(AbstractHttpConfigurer::disable)*/
                .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/**").permitAll() // Allow access without authentication
                        .requestMatchers("/index").permitAll() // Allow access without authentication
                        .requestMatchers("/register").permitAll() // Allow access without authentication
                        .requestMatchers("/login").permitAll() // Allow access without authentication
                        .requestMatchers("/profile**").permitAll() // Allow access without authentication
                        .requestMatchers("/cart").permitAll()
                        .requestMatchers("/addToCart").permitAll()
                        .requestMatchers("/products**").permitAll()
                        .requestMatchers("/orders").permitAll()
                        .requestMatchers("/confirmOrder/{orderId}").permitAll()
                        .requestMatchers("/declineOrder/{orderId}").permitAll()
                        .requestMatchers("/confirmCart").permitAll()
                        .requestMatchers("/declineCart").permitAll()
                        .requestMatchers("/add-product").permitAll()
                        .requestMatchers("/products").hasAnyAuthority("SELLER"/*, "CUSTOMER"*/)
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(
//                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .defaultSuccessUrl("/products")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Set the logout URL
                        .logoutSuccessUrl("/") // Redirect after successful logout
                        .invalidateHttpSession(true) // Invalidate the session
                        .deleteCookies("JSESSIONID") // Delete cookies
                        .permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}
