package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.dto.RegisterRequest;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.personal_finance_manager.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collections;
import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        User user = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse("User registered successfully", user.getId()));
    }


    static class RegisterResponse {
        private String message;
        private Long userId;

        public RegisterResponse(String message, Long userId) {
            this.message = message;
            this.userId = userId;
        }

        public String getMessage() {
            return message;
        }

        public Long getUserId() {
            return userId;
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        User user = userService.login(request.getUsername(), request.getPassword());

        // Creating Authentication object
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        Collections.emptyList()
                );

        // Setting authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Creating session & store security context
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // store user for business logic
        session.setAttribute("user", user);
        session.setAttribute("USER_ID", user.getId());

        return ResponseEntity.ok("Login successful");
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("Logged out successfully");
    }


}
