package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.dto.RegisterRequest;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.CategoryRepository;
import com.example.personal_finance_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_success() {

        // no existing user
        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.empty());

        // user save
        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // default categories creation
        when(categoryRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        // ✅ DTO (NOT entity)
        RegisterRequest request = new RegisterRequest();
        request.setFullName("Test User");
        request.setUsername("test@gmail.com");
        request.setPassword("password");
        request.setPhoneNumber("9999999999");

        User saved = userService.registerUser(request);

        assertNotNull(saved);
        assertEquals("test@gmail.com", saved.getUsername());
    }
}
