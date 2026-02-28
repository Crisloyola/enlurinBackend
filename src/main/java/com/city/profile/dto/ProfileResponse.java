package com.city.profile.dto;

import com.city.profile.Profile;
// lombok removed

public class ProfileResponse {

    private Long id;
    private String businessName;
    private String slug;
    private String description;
    private String logoUrl;
    private String category;
    private String district;

    public ProfileResponse(Long id, String businessName, String slug, String description, String logoUrl, String category, String district) {
        this.id = id;
        this.businessName = businessName;
        this.slug = slug;
        this.description = description;
        this.logoUrl = logoUrl;
        this.category = category;
        this.district = district;
    }

    public Long getId() { return id; }
    public String getBusinessName() { return businessName; }
    public String getSlug() { return slug; }
    public String getDescription() { return description; }
    public String getLogoUrl() { return logoUrl; }
    public String getCategory() { return category; }
    public String getDistrict() { return district; }

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