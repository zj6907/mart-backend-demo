package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private Float price;
    private String description;
    private String imageUrl;
    private Integer categoryId;
}
