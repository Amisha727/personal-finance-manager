
package com.example.personal_finance_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for REST APIs (needed for POST /api/auth/**)
                .csrf(csrf -> csrf.disable())

                // Disable default login form
                .formLogin(form -> form.disable())

                // Disable HTTP Basic auth (we handle auth manually)
                .httpBasic(httpBasic -> httpBasic.disable())

                // Configure which endpoints are public
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",   // register & login
                                "/api/health"     // health check
                        ).permitAll()
                        .anyRequest().authenticated() // everything else needs authentication
                );

        return http.build();
    }
}
