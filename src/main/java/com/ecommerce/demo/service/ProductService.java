package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;

    public void add(ProductDto dto, Category category) {
        Product p = new Product();
        transBasic(dto, p);
        p.setCategory(category);
        repo.save(p);
    }

    public List<ProductDto> findAll() {
        List<Product> products = repo.findAll();
        return transToDtos(products);
    }

    public void update(ProductDto dto, Category c) {
        Product p = repo.findById(dto.getId()).get();
        transBasic(dto, p);
        p.setId(dto.getId());
        p.setCategory(c);
        repo.save(p);
    }


    public boolean exist(Integer id) {
        return repo.existsById(id);
    }

    public void transBasic(ProductDto dto, Product p) {
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setDescription(dto.getDescription());
        p.setImageUrl(dto.getImageUrl());
    }


    public List<ProductDto> transToDtos(List<Product> products) {
        List<ProductDto> dtos = new ArrayList<>();
        for (Product p : products) {
            dtos.add(transToDto(p));
        }
        return dtos;
    }

    public ProductDto transToDto(Product p) {
        return new ProductDto() {{
            setId(p.getId());
            setName(p.getName());
            setPrice(p.getPrice());
            setDescription(p.getDescription());
            setImageUrl(p.getImageUrl());
            setCategoryId(p.getCategory().getId());
        }};
    }


    public Product checkProduct(Integer productId) {
        if (productId == null) throw new CustomException("Product id cannot be empty!");
        Product p = repo.findById(productId).orElse(null);
        if (p == null) throw new CustomException("Product not exist!");
        return p;
    }
}
