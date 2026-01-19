package com.city.role;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // USER, ADMIN

      // 🔹 Constructor vacío (OBLIGATORIO para JPA)
    public Role() {
    }

    // 🔹 Constructor que estás usando
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}