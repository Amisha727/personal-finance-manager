package com.example.personal_finance_manager.repository;

import com.example.personal_finance_manager.entity.SavingsGoal;
import com.example.personal_finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByUser(User user);
}
