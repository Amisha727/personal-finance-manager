package com.example.personal_finance_manager.dto;

import java.util.Map;

public class MonthlyReportResponse {

    private Map<String, Double> incomeByCategory;
    private Map<String, Double> expenseByCategory;
    private double netSavings;

    public MonthlyReportResponse(
            Map<String, Double> incomeByCategory,
            Map<String, Double> expenseByCategory,
            double netSavings
    ) {
        this.incomeByCategory = incomeByCategory;
        this.expenseByCategory = expenseByCategory;
        this.netSavings = netSavings;
    }

    public Map<String, Double> getIncomeByCategory() {
        return incomeByCategory;
    }

    public Map<String, Double> getExpenseByCategory() {
        return expenseByCategory;
    }

    public double getNetSavings() {
        return netSavings;
    }
}
