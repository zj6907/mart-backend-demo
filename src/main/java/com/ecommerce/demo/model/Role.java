package com.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Integer id;

    @Column(unique = true, nullable = false)
    private String name;


}
