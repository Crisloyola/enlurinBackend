package com.city.role;


import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // ROLE_USER, ROLE_ADMIN

    @Override
    public String getAuthority() {
        return name;
    }

      // 🔹 Constructor vacío (OBLIGATORIO para JPA)
    public Role() {
    }

    // 🔹 Constructor que estás usando
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}