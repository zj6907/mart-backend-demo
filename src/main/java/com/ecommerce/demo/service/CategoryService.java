package com.ecommerce.demo.service;

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

    public boolean exist(int id) {
        return repo.existsById(id);
    }
}
