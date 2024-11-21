package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.service.CategoryService;
import com.ecommerce.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService service;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> add(@RequestBody ProductDto dto) {
        Optional<Category> c = categoryService.findById(dto.getCategoryId());
        if (c.isEmpty()) {
            return new ResponseEntity<>(new APIResponse(false, "category not exist!"), HttpStatus.BAD_REQUEST);
        }
        service.add(dto, c.get());
        return new ResponseEntity<>(new APIResponse(true, "Product successfully added!"), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDto>> findAll() {
        List<ProductDto> dtos = service.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<APIResponse> update(@RequestBody ProductDto dto) {
        Optional<Category> c = categoryService.findById(dto.getCategoryId());
        if (c.isEmpty()) {
            return new ResponseEntity<>(new APIResponse(false, "Category not exist!"), HttpStatus.BAD_REQUEST);
        }
        if (!service.exist(dto.getId())) {
            return new ResponseEntity<>(new APIResponse(false, "Product not exist!"), HttpStatus.BAD_REQUEST);
        }
        service.update(dto, c.get());
        return new ResponseEntity<>(new APIResponse(true, "Product successfully added!"), HttpStatus.OK);
    }

}
