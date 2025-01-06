package com.ecommerce.demo.dto.cart;

import com.ecommerce.demo.model.Product;
import lombok.Data;

@Data
public class CartitemDto {
    private Integer id;
    private Product product;
    private Integer quantity;
    private Double totalPrice;
}
