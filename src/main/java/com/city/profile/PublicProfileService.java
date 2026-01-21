package com.city.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicProfileService {

    private final ProfileRepository profileRepository;

    public List<Profile> listProfiles(String category, String district) {
        return profileRepository
                .findByCategory_SlugAndDistrict_Slug(category, district);
    }

    public Profile getProfile(String category, String district, String slug) {
        return profileRepository
                .findBySlugAndCategory_SlugAndDistrict_Slug(slug, category, district)
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
    }
}
