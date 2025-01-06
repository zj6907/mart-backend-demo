package com.ecommerce.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "cartitems")
@Data
public class Cartitem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private @NotNull UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private @NotNull Product product;

    private @NotNull Integer quantity;

    private @NotNull Date createdDate;

    public Cartitem() {
    }

    public Cartitem(UserEntity user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.createdDate = new Date();
    }

}