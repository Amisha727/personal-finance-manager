package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.entity.SavingsGoal;
import com.example.personal_finance_manager.service.SavingsGoalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.personal_finance_manager.dto.SavingsGoalProgressResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class SavingsGoalController {

    private final SavingsGoalService savingsGoalService;

    public SavingsGoalController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @PostMapping
    public ResponseEntity<?> createGoal(
            @RequestParam String name,
            @RequestParam BigDecimal targetAmount,
            @RequestParam LocalDate targetDate,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        SavingsGoal goal = savingsGoalService.createGoal(
                userId, name, targetAmount, targetDate
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(goal);
    }

    @GetMapping
    public ResponseEntity<List<SavingsGoal>> getGoals(HttpServletRequest request) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        return ResponseEntity.ok(
                savingsGoalService.getGoals(userId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(
            @PathVariable Long id,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        savingsGoalService.deleteGoal(id, userId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/progress")
    public ResponseEntity<?> getProgress(
            @PathVariable Long id,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        SavingsGoal goal = savingsGoalService.getGoals(userId)
                .stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        BigDecimal savedAmount = savingsGoalService.calculateProgress(goal);

        return ResponseEntity.ok(
                new SavingsGoalProgressResponse(
                        goal.getId(),
                        goal.getName(),
                        savedAmount,
                        goal.getTargetAmount()
                )
        );
    }



}
