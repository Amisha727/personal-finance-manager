package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.repository.SavingsGoalRepository;
import com.example.personal_finance_manager.repository.TransactionRepository;
import com.example.personal_finance_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SavingsGoalServiceTest {

    @Mock
    private SavingsGoalRepository savingsGoalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private SavingsGoalService savingsGoalService;

    @Test
    void serviceLoads() {
        assertNotNull(savingsGoalService);
    }
}
