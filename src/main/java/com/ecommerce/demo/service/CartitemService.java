package com.ecommerce.demo.service;

import com.ecommerce.demo.model.Cartitem;
import com.ecommerce.demo.repository.CartitemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartitemService {
    @Autowired
    CartitemRepo repo;

    public void save(Cartitem cartitem) {
        repo.save(cartitem);
    }
}
