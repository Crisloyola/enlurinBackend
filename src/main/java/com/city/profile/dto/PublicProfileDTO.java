package com.city.profile.dto;

public class PublicProfileDTO {

    private String businessName;
    private String description;
    private String phone;
    private String address;

    private String category;
    private String district;
    private String slug;
    private String logoUrl;

    public PublicProfileDTO(
            String businessName,
            String description,
            String phone,
            String address,
            String category,
            String district,
            String slug,
            String logoUrl
    ) {
        this.businessName = businessName;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.category = category;
        this.district = district;
        this.slug = slug;
        this.logoUrl = logoUrl;
    }

    // getters
}
