package com.example.btsconcert.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                // Allow registration, login, homepage, and concerts without authentication
                .requestMatchers("/", "/register", "/login", "/my-tickets", "/tickets/*/delete", "/concerts/**", "/css/**", "/images/**").permitAll()
                // Restrict manager pages
                .requestMatchers("/manager/**").hasRole("MANAGER")
                // Everything else requires authentication
                .anyRequest().authenticated()
            )
            // Login configuration
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler) // Use your custom handler
                .permitAll()
            )
            // Logout configuration
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                    .accessDeniedPage("/access-denied")
            )
            // Optional: disable CSRF only if needed
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // Add this bean to fix the “PasswordEncoder not found” error
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
