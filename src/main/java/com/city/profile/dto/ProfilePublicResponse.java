package com.city.profile.dto;

import com.city.profile.Profile;

public class ProfilePublicResponse {

    private Long id;
    private String businessName;
    private String slug;
    private String description;
    private String phone;
    private String address;
    private String logoUrl;

    public static ProfilePublicResponse from(Profile profile) {
        ProfilePublicResponse dto = new ProfilePublicResponse();
        dto.id = profile.getId();
        dto.businessName = profile.getBusinessName();
        dto.slug = profile.getSlug();
        dto.description = profile.getDescription();
        dto.phone = profile.getPhone();
        dto.address = profile.getAddress();
        dto.logoUrl = profile.getLogoUrl();
        return dto;
    }

    // getters
    public Long getId() { return id; }
    public String getBusinessName() { return businessName; }
    public String getSlug() { return slug; }
    public String getDescription() { return description; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getLogoUrl() { return logoUrl; }
}
