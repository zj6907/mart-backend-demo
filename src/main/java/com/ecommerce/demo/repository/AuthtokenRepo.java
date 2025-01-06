package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.Authtoken;
import com.ecommerce.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthtokenRepo extends JpaRepository<Authtoken, Integer> {
    Authtoken findByUser(UserEntity u);

    Authtoken findByToken(String token);
}
