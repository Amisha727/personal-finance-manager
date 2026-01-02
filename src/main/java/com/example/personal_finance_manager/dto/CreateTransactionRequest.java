package com.example.personal_finance_manager.dto;

import com.example.personal_finance_manager.entity.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateTransactionRequest {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotNull
    private TransactionType type;

    @NotNull
    private Long categoryId;

    private String description;

    // getters & setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
