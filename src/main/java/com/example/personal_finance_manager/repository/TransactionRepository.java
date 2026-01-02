package com.example.personal_finance_manager.repository;

import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Fetch all transactions of a user (newest first)
    List<Transaction> findByUserOrderByDateDesc(User user);

    // Filter by date range
    List<Transaction> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );

    // Filter by category
    List<Transaction> findByUserAndCategory(User user, String category);

    // Filter by type (INCOME / EXPENSE)
    List<Transaction> findByUserAndType(User user, String type);
}
