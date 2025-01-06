package com.ecommerce.demo.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartDto {
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
