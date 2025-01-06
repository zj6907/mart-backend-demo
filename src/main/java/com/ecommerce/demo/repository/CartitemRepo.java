package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Cartitem;
import com.ecommerce.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartitemRepo extends JpaRepository<Cartitem, Integer> {
    List<Cartitem> findAllByUser(UserEntity user);
}
