package com.city.profile;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicProfileService {

    private final ProfileRepository profileRepository;

    public Profile getProfile(
            String category,
            String district,
            String profileSlug
    ) {
        return profileRepository
                .findBySlugAndCategory_SlugAndDistrict_Slug(
                        profileSlug, category, district
                )
                .orElseThrow(() ->
                        new RuntimeException("Negocio no encontrado"));
    }

    public List<Profile> listProfiles(
            String category,
            String district
    ) {
        return profileRepository
                .findByCategory_SlugAndDistrict_Slug(category, district);
    }


}