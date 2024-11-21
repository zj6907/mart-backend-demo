package com.ecommerce.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "wishlist")
@Data
public class Wishlist {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private @NotNull Date createdDate;

    public Wishlist() {
    }

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
    }
}
