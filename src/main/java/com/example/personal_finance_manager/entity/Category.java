package com.example.personal_finance_manager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
