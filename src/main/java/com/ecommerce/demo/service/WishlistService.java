package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.model.Wishlist;
import com.ecommerce.demo.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    WishlistRepo repo;
    @Autowired
    ProductService productService;

    public void save(User user, ProductDto dto) {
        Product p = new Product();
        //productService.transBasic(dto, p);
        p.setId(dto.getId());
        p.setName(dto.getName());
        // Cannot add existing product for same user!
        Wishlist exist = repo.findByUserAndProduct(user, p);
        if (exist != null)
            throw new CustomException("product [" + p.getName() + "] for user [" + user.getFirstName() + "] already added!");
        repo.save(new Wishlist(user, p));
    }

    public List<ProductDto> findAll(User user) {
        List<Wishlist> wz = repo.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> dtos = new ArrayList<>();
        for (Wishlist w : wz) {
            dtos.add(productService.transToDto(w.getProduct()));
        }
        return dtos;
    }

}
