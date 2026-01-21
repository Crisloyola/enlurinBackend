package com.city.profile.dto;

public class ProfileUpdateRequest {

    private String description;
    private String phone;
    private String address;
    private String categorySlug;
    private String districtSlug;

    public String getDescription() { return description; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getCategorySlug() { return categorySlug; }
    public String getDistrictSlug() { return districtSlug; }
}
