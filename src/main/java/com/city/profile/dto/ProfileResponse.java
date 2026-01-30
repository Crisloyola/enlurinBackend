package com.city.profile.dto;

import com.city.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String businessName;
    private String slug;
    private String description;
    private String logoUrl;
    private String category;
    private String district;

    public static ProfileResponse from(Profile p) {
        return new ProfileResponse(
                p.getId(),
                p.getBusinessName(),
                p.getSlug(),
                p.getDescription(),
                p.getLogoUrl(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getDistrict() != null ? p.getDistrict().getName() : null
        );
    }
}