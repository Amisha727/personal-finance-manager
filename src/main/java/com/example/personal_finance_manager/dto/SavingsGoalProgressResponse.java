package com.example.personal_finance_manager.dto;

import java.math.BigDecimal;

public class SavingsGoalProgressResponse {

    private Long goalId;
    private String goalName;
    private BigDecimal savedAmount;
    private BigDecimal targetAmount;
    private int progressPercentage;

    public SavingsGoalProgressResponse(
            Long goalId,
            String goalName,
            BigDecimal savedAmount,
            BigDecimal targetAmount
    ) {
        this.goalId = goalId;
        this.goalName = goalName;
        this.savedAmount = savedAmount;
        this.targetAmount = targetAmount;

        this.progressPercentage = targetAmount.compareTo(BigDecimal.ZERO) == 0
                ? 0
                : savedAmount
                .multiply(BigDecimal.valueOf(100))
                .divide(targetAmount, 0, BigDecimal.ROUND_DOWN)
                .intValue();
    }

    public Long getGoalId() { return goalId; }
    public String getGoalName() { return goalName; }
    public BigDecimal getSavedAmount() { return savedAmount; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public int getProgressPercentage() { return progressPercentage; }
}
