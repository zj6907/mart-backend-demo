package com.ecommerce.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;

    @Column(name = "first_name")
    private @NotNull String firstName;

    @Column(name = "last_name")
    private @NotNull String lastName;

    private @NotNull String email;

    private @NotNull String password;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
