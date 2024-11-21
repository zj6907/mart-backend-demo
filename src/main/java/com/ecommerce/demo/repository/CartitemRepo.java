package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartitemRepo extends JpaRepository<Cartitem, Integer> {
}
