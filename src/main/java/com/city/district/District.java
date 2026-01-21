package com.city.district;

import jakarta.persistence.*;

@Entity
@Table(name = "districts")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
        private String slug;


    public District() {}

    public District(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}