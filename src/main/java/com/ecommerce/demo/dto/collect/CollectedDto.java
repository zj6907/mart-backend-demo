package com.ecommerce.demo.dto.collect;

import com.ecommerce.demo.dto.ProductDto;
import lombok.Data;

@Data
public class CollectedDto {
    private Integer id;
    private ProductDto product;
}
