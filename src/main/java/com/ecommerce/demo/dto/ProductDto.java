package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Integer categoryId;

    public ProductDto() {
    }

    public ProductDto(Integer id, String name, Double price, String description, String imageUrl, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }
}
