package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;
    @Autowired
    CategoryService categoryService;

    public void add(ProductDto dto) {
        Optional<Category> c = categoryService.findById(dto.getCategoryId());
        if (c.isEmpty()) {
            throw new CustomException("Category doesn't exist!");
        }
        Product p = new Product();
        transBasic(dto, p);
        p.setCategory(c.get());
        repo.save(p);
    }

    public List<ProductDto> findAll() {
        List<Product> products = repo.findAll();
        return transToDtos(products);
    }

    public Page<ProductDto> getPaginatedProductDtos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Creates a Pageable object
        return getPageable(pageable);
    }

    private Page<ProductDto> getPageable(Pageable pageable) {
        Page<Product> ppage = repo.findAll(pageable);// Fetch paginated results
        return ppage.map(p -> {
            ProductDto dto = transToDto(p);
            return dto;
        });
    }

    public Page<ProductDto> getSortedPaginatedProductDtos(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return getPageable(pageable);
    }

    public void update(ProductDto dto) {
        Optional<Category> c = categoryService.findById(dto.getCategoryId());
        if (c.isEmpty()) {
            throw new CustomException("Category not exist!");
        }
        if (!exist(dto.getId())) {
            throw new CustomException("Product not exist!");
        }
        Product p = repo.findById(dto.getId()).get();
        transBasic(dto, p);
        p.setCategory(c.get());
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

    public void addAll(List<ProductDto> dtos) {
        List<Product> products = new ArrayList<>();
        dtos.forEach(dto -> {
            if (dto.getCategoryId() == null) {
                throw new CustomException("Category for product `" + dto.getName() + "`must not be empty!");
            }
            Optional<Category> c = categoryService.findById(dto.getCategoryId());
            if (c.isEmpty()) {
                throw new CustomException("Category for product `" + dto.getName() + "`must not be empty!");
            }
            Product p = new Product();
            transBasic(dto, p);
            p.setCategory(c.get());
            products.add(p);
        });
        repo.saveAll(products);
    }
}
