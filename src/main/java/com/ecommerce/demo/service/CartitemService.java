package com.ecommerce.demo.service;

import com.ecommerce.demo.model.Cartitem;
import com.ecommerce.demo.model.UserEntity;
import com.ecommerce.demo.repository.CartitemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartitemService {
    @Autowired
    CartitemRepo repo;

    public void save(Cartitem cartitem) {
        repo.save(cartitem);
    }

    public List<Cartitem> findAllByUser(UserEntity user) {
        return repo.findAllByUser(user);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
