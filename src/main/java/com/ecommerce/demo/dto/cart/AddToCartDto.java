package com.ecommerce.demo.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartDto {
    private Integer id;
    private @NotNull Integer userId;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
