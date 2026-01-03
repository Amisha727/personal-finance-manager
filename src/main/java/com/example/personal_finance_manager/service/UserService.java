package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.dto.RegisterRequest;
import com.example.personal_finance_manager.entity.Category;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.CategoryRepository;
import com.example.personal_finance_manager.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());

        // SAVE USER FIRST
        User savedUser = userRepository.save(user);

        // CREATE DEFAULT CATEGORIES (PDF requirement)
        Category income = new Category();
        income.setName("Income");
        income.setDefault(true);
        income.setUser(savedUser);

        Category expense = new Category();
        expense.setName("Expense");
        expense.setDefault(true);
        expense.setUser(savedUser);

        categoryRepository.save(income);
        categoryRepository.save(expense);

        // RETURN USER
        return savedUser;
    }

    public User login(String username, String rawPassword) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}
