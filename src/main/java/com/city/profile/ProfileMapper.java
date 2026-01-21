package com.city.profile;

import com.city.profile.dto.ProfileResponse;

public class ProfileMapper {

    public static ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getBusinessName(),
                profile.getDescription(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getSlug(),
                profile.getLogoUrl(),
                profile.getCategory().getName(),
                profile.getDistrict().getName()
        );
    }
}