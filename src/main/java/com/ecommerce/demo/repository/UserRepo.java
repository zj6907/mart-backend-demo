package com.ecommerce.demo.repository;

import com.ecommerce.demo.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public boolean existsByEmail(String email);

    User findByEmail(@NotNull String email);
}
