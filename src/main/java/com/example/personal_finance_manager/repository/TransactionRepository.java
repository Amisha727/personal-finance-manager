package com.example.personal_finance_manager.repository;

import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Fetching all transactions of a user
    boolean existsByCategory(Category category);

    List<Transaction> findByUserOrderByDateDesc(User user);

    // Filter by date range
    List<Transaction> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );
    @Query("""
        SELECT t FROM Transaction t
        WHERE t.user = :user
        AND t.date BETWEEN :start AND :end
    """)


    List<Transaction> findByUserAndDateRange(
            @Param("user") User user,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // Filter by category
    List<Transaction> findByUserAndCategory(User user, Category category);

    // Filter by type (INCOME / EXPENSE)
    List<Transaction> findByUserAndType(User user, String type);

    // Check if category is used in any transaction

}
