package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.entity.Category;
import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.entity.TransactionType;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // CREATING TRANSACTION
    public Transaction createTransaction(
            Long userId,
            BigDecimal amount,
            LocalDate date,
            TransactionType type,
            Long categoryId,
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

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setType(type);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    // GETING ALL TRANSACTIONS
    public List<Transaction> getAllTransactions(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionRepository.findByUserOrderByDateDesc(user);
    }

    // UPDATE TRANSACTION
    public Transaction updateTransaction(
            Long transactionId,
            Long userId,
            BigDecimal amount,
            TransactionType type,
            Long categoryId,
            String description
    ) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            transaction.setAmount(amount);
        }

        if (type != null) {
            transaction.setType(type);
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            transaction.setCategory(category);
        }

        if (description != null) {
            transaction.setDescription(description);
        }

        return transactionRepository.save(transaction);
    }

    // DELETE TRANSACTION
    public void deleteTransaction(Long transactionId, Long userId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        transactionRepository.delete(transaction);
    }
}
