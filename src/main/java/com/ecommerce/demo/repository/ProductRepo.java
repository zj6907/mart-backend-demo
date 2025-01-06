package com.ecommerce.demo.repository;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.ecommerce.demo.dto.ProductDto(p.id, p.name, p.price, p.description, p.imageUrl, p.category.id) FROM Product p")
    Page<ProductDto> findAllProductDtos(Pageable pageable);

}
