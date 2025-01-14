package com.ecommerce.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Data
public class Authtoken {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;

    private @NotNull String token;

    @Column(name = "created_date")
    private @NotNull Date createdDate;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Authtoken() {
    }

    public Authtoken(UserEntity u) {
        this.user = u;
        this.token = UUID.randomUUID().toString();
        this.createdDate = new Date();
    }
}
