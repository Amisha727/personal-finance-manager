package com.example.personal_finance_manager.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal targetAmount;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Column(nullable = false)
    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
