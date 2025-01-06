package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    boolean existsByCategoryName(@NotBlank String categoryName);

    Category findByCategoryName(String name);
}
