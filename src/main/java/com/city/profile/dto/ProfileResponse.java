package com.city.profile.dto;

import com.city.profile.Profile;

public class ProfileResponse {

    private Long id;
    private String businessName;
    private String slug;
    private String description;
    private String phone;
    private String address;
    private String logoUrl;
    private String category;
    private String district;
    private String status;
    private boolean featured;

    public ProfileResponse(Long id, String businessName, String slug,
                           String description, String phone, String address,
                           String logoUrl, String category, String district,
                           String status, boolean featured) {
        this.id           = id;
        this.businessName = businessName;
        this.slug         = slug;
        this.description  = description;
        this.phone        = phone;
        this.address      = address;
        this.logoUrl      = logoUrl;
        this.category     = category;
        this.district     = district;
        this.status       = status;
        this.featured     = featured;
    }

    public Long getId()            { return id; }
    public String getBusinessName(){ return businessName; }
    public String getSlug()        { return slug; }
    public String getDescription() { return description; }
    public String getPhone()       { return phone; }
    public String getAddress()     { return address; }
    public String getLogoUrl()     { return logoUrl; }
    public String getCategory()    { return category; }
    public String getDistrict()    { return district; }
    public String getStatus()      { return status; }
    public boolean isFeatured()    { return featured; }

    public static ProfileResponse from(Profile p) {
        return new ProfileResponse(
            p.getId(),
            p.getBusinessName(),
            p.getSlug(),
            p.getDescription(),
            p.getPhone(),
            p.getAddress(),
            p.getLogoUrl(),
            p.getCategory() != null ? p.getCategory().getName() : null,
            p.getDistrict()  != null ? p.getDistrict().getName()  : null,
            p.getStatus()    != null ? p.getStatus().name()        : null,
            p.isFeatured()
        );
    }
}