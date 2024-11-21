package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findAllByUserOrderByCreatedDateDesc(User user);

    Wishlist findByUserAndProduct(User user, Product p);
}
