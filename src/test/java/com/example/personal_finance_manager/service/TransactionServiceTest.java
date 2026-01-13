package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.entity.Category;
import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.entity.TransactionType;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.CategoryRepository;
import com.example.personal_finance_manager.repository.TransactionRepository;
import com.example.personal_finance_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createTransaction_success() {

        User user = new User();
        user.setId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Salary");

        doReturn(Optional.of(user))
                .when(userRepository)
                .findById(anyLong());



        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Transaction saved = transactionService.createTransaction(
                1L,
                BigDecimal.valueOf(1000),
                LocalDate.now(),
                TransactionType.INCOME,
                1L,
                "Test income"
        );

        assertNotNull(saved);
        assertEquals(TransactionType.INCOME, saved.getType());
    }
}
