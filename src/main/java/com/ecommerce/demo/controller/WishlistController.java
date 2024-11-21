package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.service.AuthtokenService;
import com.ecommerce.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistService service;
    @Autowired
    AuthtokenService authtokenService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addWishlist(ProductDto dto, String token) {
        // Authenticate the token and return the user
        User user = authtokenService.authenticate(token);
        // Save the wishlist
        service.save(user, dto);
        return new ResponseEntity<>(new APIResponse(true, "Wishlist created!"), HttpStatus.CREATED);
    }

    @PostMapping("/findAll")
    public List<ProductDto> findAllForUser(String token) {
        User user = authtokenService.authenticate(token);
        return service.findAll(user);
    }

}
