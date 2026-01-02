package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.dto.MonthlyReportResponse;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyReportResponse> getMonthlyReport(
            @RequestParam int month,
            @RequestParam int year,
            HttpServletRequest request
    ) {

        User user = (User) request.getSession(false).getAttribute("user");

        MonthlyReportResponse report =
                reportService.getMonthlyReport(user, month, year);

        return ResponseEntity.ok(report);
    }
}
