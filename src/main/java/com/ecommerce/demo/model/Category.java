package com.ecommerce.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;

    @Column(name = "category_name")
    private @NotBlank String categoryName;

    private @NotBlank String description;

    @Column(name = "image_url")
    private @NotBlank String imageUrl;

}
