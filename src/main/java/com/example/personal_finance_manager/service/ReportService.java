package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.dto.MonthlyReportResponse;
import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.entity.TransactionType;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.personal_finance_manager.entity.TransactionType.*;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MonthlyReportResponse getMonthlyReport(User user, int month, int year) {

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        List<Transaction> transactions =
                transactionRepository.findByUserAndDateRange(user, start, end);

        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {

            String category = t.getCategory().getName();
            double amount = t.getAmount().doubleValue();

            if (t.getType() == TransactionType.INCOME) {
                incomeMap.put(category,
                        incomeMap.getOrDefault(category, 0.0) + amount);
                totalIncome += amount;
            } else {
                expenseMap.put(category,
                        expenseMap.getOrDefault(category, 0.0) + amount);
                totalExpense += amount;
            }
        }

        return new MonthlyReportResponse(
                incomeMap,
                expenseMap,
                totalIncome - totalExpense
        );
    }
}
