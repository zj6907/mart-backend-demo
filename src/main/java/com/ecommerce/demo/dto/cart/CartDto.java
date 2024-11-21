package com.ecommerce.demo.dto.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private List<CartitemDto> cartitems;
    private double totalCost;
}
