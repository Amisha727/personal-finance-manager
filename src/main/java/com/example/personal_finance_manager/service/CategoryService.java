package com.example.personal_finance_manager.service;

import com.example.personal_finance_manager.entity.Category;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.repository.CategoryRepository;
import com.example.personal_finance_manager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public Category createCategory(String name, User user) {

        if (categoryRepository.existsByNameAndUser(name, user)) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);
        category.setDefault(false);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public List<Category> getCategories(User user) {
        return categoryRepository.findByUser(user);
    }

    public void deleteCategory(Long id, User user) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.isDefault()) {
            throw new RuntimeException("Default category cannot be deleted");
        }

        if (transactionRepository.existsByCategory(category)) {
            throw new RuntimeException("Category is used in transactions");
        }

        categoryRepository.delete(category);
    }
}
