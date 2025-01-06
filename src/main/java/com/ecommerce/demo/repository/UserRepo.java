package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);

    UserEntity findByEmail(@NotNull String email);
}
