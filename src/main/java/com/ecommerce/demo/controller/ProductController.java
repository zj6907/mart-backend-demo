package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<APIResponse> add(@RequestBody ProductDto dto) {
        if (dto.getCategoryId() == null) {
            return new ResponseEntity<>(new APIResponse(false, "Category must not be empty!"), HttpStatus.BAD_REQUEST);
        }

        service.add(dto);
        return new ResponseEntity<>(new APIResponse(true, "Product successfully added!"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addAll")
    public ResponseEntity<APIResponse> addAll(@RequestBody List<ProductDto> dtos) {
        if (dtos.isEmpty()) {
            return new ResponseEntity<>(new APIResponse(false, "Product must not be empty!"), HttpStatus.BAD_REQUEST);
        }

        service.addAll(dtos);
        return new ResponseEntity<>(new APIResponse(true, dtos.size() + " products added successfully!"), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> update(@RequestBody ProductDto dto) {
        service.update(dto);
        return new ResponseEntity<>(new APIResponse(true, "Product successfully updated!"), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDto>> findAll() {
        List<ProductDto> dtos = service.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/paginate")
    public ResponseEntity<Page<ProductDto>> getPaginatedProductDtos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ProductDto> dtoPage = service.getPaginatedProductDtos(page, size);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    // Optional: With sorting
    @GetMapping("/paginate/sorted")
    public ResponseEntity<Page<ProductDto>> getSortedPaginatedProductDtos(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy) {
        Page<ProductDto> dtoPage = service.getSortedPaginatedProductDtos(page, size, sortBy);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

}
