package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.dto.cart.AddToCartDto;
import com.ecommerce.demo.dto.cart.CartDto;
import com.ecommerce.demo.dto.cart.CartitemDto;
import com.ecommerce.demo.model.Cartitem;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.service.CartitemService;
import com.ecommerce.demo.service.ProductService;
import com.ecommerce.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cartitem")
public class CartitemController {
    @Autowired
    CartitemService service;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(("/add"))
    public ResponseEntity<APIResponse> addToCart(@RequestBody AddToCartDto dto, @AuthenticationPrincipal User u) {
        // Check product
        Integer productId = dto.getProductId();
        Product p = productService.checkProduct(productId);
        // Add cart item
        service.save(new Cartitem(userService.findByEmail(u.getUsername()), p, dto.getQuantity()));
        return new ResponseEntity<>(new APIResponse(true, "Product [" + p.getName() + "] added to cart!"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/findAll")
    public ResponseEntity<CartDto> findAllByUser(@AuthenticationPrincipal(errorOnInvalidType = true) User u) {
        List<Cartitem> cartitems = service.findAllByUser(userService.findByEmail(u.getUsername()));
        List<CartitemDto> cartitemDtos = new ArrayList<>();
        Double totalPrice = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (Cartitem i : cartitems) {
            CartitemDto itemDto = new CartitemDto();
            itemDto.setId(i.getId());
            itemDto.setProduct(i.getProduct());
            itemDto.setQuantity(i.getQuantity());
            double itemPrice = Double.parseDouble(decimalFormat.format(i.getQuantity() * i.getProduct().getPrice()));
            itemDto.setTotalPrice(itemPrice);
            cartitemDtos.add(itemDto);
            totalPrice += itemPrice;
        }
        CartDto dto = new CartDto();
        dto.setCartitems(cartitemDtos);
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
        dto.setTotalPrice(totalPrice);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping(("/delete/{id}"))
    public ResponseEntity<APIResponse> deleteFromCart(@PathVariable Integer id) {
        // Delete item
        service.delete(id);
        return new ResponseEntity<>(new APIResponse(true, "Product deleted!"), HttpStatus.OK);
    }

}
