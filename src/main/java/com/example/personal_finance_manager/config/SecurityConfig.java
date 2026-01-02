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
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/health",
                                "/api/transactions/**"   // 👈 IMPORTANT
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.maximumSessions(1)
                );

        return http.build();
    }
}
