package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.dto.cart.AddToCartDto;
import com.ecommerce.demo.dto.cart.CartDto;
import com.ecommerce.demo.dto.cart.CartitemDto;
import com.ecommerce.demo.model.Cartitem;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.service.AuthtokenService;
import com.ecommerce.demo.service.CartitemService;
import com.ecommerce.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cartitem")
public class CartitemController {
    @Autowired
    CartitemService service;
    @Autowired
    AuthtokenService authtokenService;
    @Autowired
    ProductService productService;

    @PostMapping(("/add"))
    public ResponseEntity<APIResponse> addToCart(AddToCartDto dto, String token) {
        // Check token
        User user = authtokenService.authenticate(token);
        // Check product
        Integer productId = dto.getProductId();
        Product p = productService.checkProduct(productId);
        // Add cart item
        service.save(new Cartitem(user, p, dto.getQuantity()));
        return new ResponseEntity<>(new APIResponse(true, "Cartitem created!"), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<CartDto> findAll(String token) {
        // Check token
        User user = authtokenService.authenticate(token);
        // Get cart items and calculate total price

        CartDto cartDto = null;
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
