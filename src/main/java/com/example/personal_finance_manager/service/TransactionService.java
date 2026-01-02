package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.TransactionRepository;
import com.example.personal_finance_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(
            Long userId,
            BigDecimal amount,
            LocalDate date,
            String type,
            String category,
            String description
    ) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new RuntimeException("Date cannot be in the future");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setType(type);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUserOrderByDateDesc(user);
    }
}
