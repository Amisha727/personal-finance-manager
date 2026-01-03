package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.entity.SavingsGoal;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.entity.TransactionType;
import com.example.personal_finance_manager.repository.SavingsGoalRepository;
import com.example.personal_finance_manager.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.repository.TransactionRepository;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    public SavingsGoalService(SavingsGoalRepository savingsGoalRepository,
                              UserRepository userRepository, TransactionRepository transactionRepository) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public SavingsGoal createGoal(Long userId, String name,
                                  BigDecimal targetAmount, LocalDate targetDate) {

        if (targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Target amount must be positive");
        }

        if (!targetDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Target date must be in future");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SavingsGoal goal = new SavingsGoal();
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setTargetDate(targetDate);
        goal.setStartDate(LocalDate.now());
        goal.setUser(user);

        return savingsGoalRepository.save(goal);
    }

    public List<SavingsGoal> getGoals(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return savingsGoalRepository.findByUser(user);
    }

    public void deleteGoal(Long goalId, Long userId) {

        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!goal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        savingsGoalRepository.delete(goal);
    }
    public BigDecimal calculateProgress(SavingsGoal goal) {

        List<Transaction> transactions =
                transactionRepository.findByUserAndDateRange(
                        goal.getUser(),
                        goal.getStartDate(),
                        LocalDate.now()
                );

        BigDecimal saved = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.INCOME) {
                saved = saved.add(t.getAmount());
            }
        }

        return saved;
    }



}
