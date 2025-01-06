package com.ecommerce.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;
    private @NotNull String name;
    private @NotNull Double price;
    private @NotNull String description;
    @Column(name = "image_url")
    private @NotNull String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    Category category;
}
