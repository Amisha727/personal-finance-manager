package com.example.personal_finance_manager.controller;

import com.example.personal_finance_manager.entity.Category;
import com.example.personal_finance_manager.entity.User;
import com.example.personal_finance_manager.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestParam String name,
                                   HttpSession session) {

        User user = (User) session.getAttribute("user");
        return categoryService.createCategory(name, user);
    }

    @GetMapping
    public List<Category> getCategories(HttpSession session) {

        User user = (User) session.getAttribute("user");
        return categoryService.getCategories(user);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id,
                               HttpSession session) {

        User user = (User) session.getAttribute("user");
        categoryService.deleteCategory(id, user);
    }
}
