package com.ecommerce.demo.service;

import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo repo;

    public void createCategory(Category category) {
        repo.save(category);
    }

    public List<Category> findAll() {
        return repo.findAll();
    }

    public void updateCategory(Integer id, Category category) {
        repo.save(category);
    }

    public Optional<Category> findById(Integer id) {
        return repo.findById(id);
    }

    public Category findByName(String name) {
        return repo.findByCategoryName(name);
    }

    public boolean exist(int id) {
        return repo.existsById(id);
    }

    public void check(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            throw new CustomException("Category name cannot be empty");
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new CustomException("Description cannot be empty");
        }
        if (category.getImageUrl() == null || category.getImageUrl().isEmpty()) {
            throw new CustomException("Image url cannot be empty");
        }
        if (repo.existsByCategoryName(category.getCategoryName())) throw new CustomException("Category already exists");
    }

}
