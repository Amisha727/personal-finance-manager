package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.dto.MonthlyReportResponse;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly")
    public MonthlyReportResponse getMonthlyReport(
            @RequestParam int month,
            @RequestParam int year,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        return reportService.getMonthlyReport(user, month, year);
    }
}
