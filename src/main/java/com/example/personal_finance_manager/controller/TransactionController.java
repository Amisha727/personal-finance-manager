package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(
            @RequestParam BigDecimal amount,
            @RequestParam LocalDate date,
            @RequestParam String type,
            @RequestParam String category,
            @RequestParam(required = false) String description,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        Transaction transaction = transactionService.createTransaction(
                userId, amount, date, type, category, description
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request) {

         var session = request.getSession(false);
        if (session == null || session.getAttribute("USER_ID") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = (Long) session.getAttribute("USER_ID");


        return ResponseEntity.ok(
                transactionService.getAllTransactions(userId)
        );
    }
}
