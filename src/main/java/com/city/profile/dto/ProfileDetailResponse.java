package com.city.profile.dto;

import com.city.profile.Profile;
// lombok removed

public class ProfileDetailResponse {

    private Long id;
    private String businessName;
    private String description;
    private String phone;
    private String address;
    private String logoUrl;
    private String category;
    private String district;

    public ProfileDetailResponse(Long id, String businessName, String description, String phone, String address, String logoUrl, String category, String district) {
        this.id = id;
        this.businessName = businessName;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.logoUrl = logoUrl;
        this.category = category;
        this.district = district;
    }

    public Long getId() { return id; }
    public String getBusinessName() { return businessName; }
    public String getDescription() { return description; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getLogoUrl() { return logoUrl; }
    public String getCategory() { return category; }
    public String getDistrict() { return district; }

    public static ProfileDetailResponse from(Profile profile) {
        return new ProfileDetailResponse(
                profile.getId(),
                profile.getBusinessName(),
                profile.getDescription(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getLogoUrl(),
                profile.getCategory().getName(),
                profile.getDistrict().getName()
        );
    }
}
