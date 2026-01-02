package com.example.personal_finance_manager.repository;

import com.example.personal_finance_manager.entity.Category;
import com.example.personal_finance_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);

    boolean existsByNameAndUser(String name, User user);
}
