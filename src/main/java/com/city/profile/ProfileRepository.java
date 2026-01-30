package com.city.profile;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser_Id(Long userId);
    List<Profile> findByStatus(ProfileStatus status);
    Optional<Profile> findBySlug(String slug);
    Optional<Profile> findByUser_Email(String email);

    Optional<Profile> findBySlugAndCategory_SlugAndDistrict_Slug(
        String slug,
        String categorySlug,
        String districtSlug
    );

    List<Profile> findByCategory_SlugAndDistrict_Slug(
        String categorySlug,
        String districtSlug
    );

    List<Profile> findByStatusAndDistrict_NameAndCategory_Name(
            ProfileStatus status,
            String district,
            String category
    );

    @Query("""
        SELECT p FROM Profile p
        WHERE
            (:q IS NULL OR LOWER(p.businessName) LIKE LOWER(CONCAT('%', :q, '%')))
        AND (:category IS NULL OR p.category = :category)
        AND (:district IS NULL OR p.district = :district)
    """)
    Page<Profile> searchPublic(
            @Param("q") String q,
            @Param("category") String category,
            @Param("district") String district,
            Pageable pageable
    );

    @Query("""
        SELECT p FROM Profile p
        WHERE p.status = :status
        AND p.district.name = :district
        AND (
            LOWER(p.businessName) LIKE LOWER(CONCAT('%', :q, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%'))
        )
    """)
    List<Profile> search(
            @Param("status") ProfileStatus status,
            @Param("district") String district,
            @Param("q") String q
    );

    @Query("""
        SELECT p FROM Profile p
        WHERE p.status = :status
        AND p.district.name = :district
        AND (:q IS NULL OR LOWER(p.businessName) LIKE LOWER(CONCAT('%', :q, '%')))
        AND (:category IS NULL OR p.category.slug = :category)
        ORDER BY p.featured DESC, p.createdAt DESC
    """)
    List<Profile> searchPublicProfiles(
            @Param("status") ProfileStatus status,
            @Param("district") String district,
            @Param("q") String q,
            @Param("category") String category
    );

     // público
    List<Profile> findByStatusAndDistrict_Name(
            ProfileStatus status,
            String districtName
    );

    Optional<Profile> findBySlugAndStatus(
            String slug,
            ProfileStatus status
    );


}