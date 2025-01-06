package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.dto.collect.CollectedDto;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.UserEntity;
import com.ecommerce.demo.model.Collected;
import com.ecommerce.demo.repository.CollectedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectedService {

    @Autowired
    CollectedRepo repo;
    @Autowired
    ProductService productService;

    public void save(UserEntity user, ProductDto dto) {
        Product p = new Product();
        //productService.transBasic(dto, p);
        p.setId(dto.getId());
        p.setName(dto.getName());
        // Cannot add existing product for same user!
        Collected exist = repo.findByUserAndProduct(user, p);
        if (exist != null)
            throw new CustomException("product [" + p.getName() + "] already collected!");
        repo.save(new Collected(user, p));
    }

    public List<CollectedDto> findAll(UserEntity user) {
        List<Collected> cz = repo.findAllByUserOrderByCreatedDateDesc(user);
        List<CollectedDto> dtos = new ArrayList<>();
        for (Collected c : cz) {
            dtos.add(new CollectedDto() {{
                setId(c.getId());
                setProduct(productService.transToDto(c.getProduct()));
            }});
        }
        return dtos;
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
