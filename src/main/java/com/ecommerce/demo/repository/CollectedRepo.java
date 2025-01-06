package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.UserEntity;
import com.ecommerce.demo.model.Collected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectedRepo extends JpaRepository<Collected, Integer> {
    List<Collected> findAllByUserOrderByCreatedDateDesc(UserEntity user);

    Collected findByUserAndProduct(UserEntity user, Product p);
}
