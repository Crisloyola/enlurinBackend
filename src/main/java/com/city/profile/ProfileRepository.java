package com.city.profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByCategory_NameAndDistrict_Name(String category, String district);

    Optional<Profile> findByUser_Id(Long userId);
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
}