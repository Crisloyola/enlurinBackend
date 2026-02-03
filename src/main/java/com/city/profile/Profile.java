package com.city.profile;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.city.category.Category;
import com.city.district.District;
import com.city.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@JsonIgnoreProperties({"user"})
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;
    private String phone;
    private String address;
    private String logo; // nombre del archivo

    @Column(length = 255)
    private String logoUrl;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileStatus status = ProfileStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // ⭐ NUEVO (para destacados)
    @Column(nullable = false)
    private boolean featured = false;

}
