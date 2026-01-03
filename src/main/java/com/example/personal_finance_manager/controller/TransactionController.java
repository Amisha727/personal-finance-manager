package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.dto.CreateTransactionRequest;
import com.example.personal_finance_manager.entity.Transaction;
import com.example.personal_finance_manager.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> createTransaction(
            @Valid @RequestBody CreateTransactionRequest requestBody,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        Transaction transaction = transactionService.createTransaction(
                userId,
                requestBody.getAmount(),
                requestBody.getDate(),
                requestBody.getType(),
                requestBody.getCategoryId(),
                requestBody.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        return ResponseEntity.ok(
                transactionService.getAllTransactions(userId)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(
            @PathVariable Long id,
            @RequestBody CreateTransactionRequest requestBody,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        Transaction updated = transactionService.updateTransaction(
                id,
                userId,
                requestBody.getAmount(),
                requestBody.getType(),
                requestBody.getCategoryId(),
                requestBody.getDescription()
        );

        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(
            @PathVariable Long id,
            HttpServletRequest request
    ) {

        Long userId = (Long) request.getSession(false).getAttribute("USER_ID");

        transactionService.deleteTransaction(id, userId);

        return ResponseEntity.noContent().build();
    }
}
