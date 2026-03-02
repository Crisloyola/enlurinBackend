package com.city.profile.dto;

import java.util.List;

import com.city.profile.Profile;
import com.city.profile.ProfileMedia;

public class ProfilePublicResponse {

    private Long id;
    private String businessName;
    private String slug;
    private String description;
    private String phone;
    private String address;
    private String logoUrl;
    private String category;
    private String district;
    private boolean featured;
    private String status;
    private String whatsapp;
    private Double latitude;
    private Double longitude;
    private String schedule;
    private List<ProfileMedia> mediaItems;

   public static ProfilePublicResponse from(Profile profile) {
    ProfilePublicResponse dto = new ProfilePublicResponse();
    dto.id           = profile.getId();
    dto.businessName = profile.getBusinessName();
    dto.slug         = profile.getSlug();
    dto.description  = profile.getDescription();
    dto.phone        = profile.getPhone();
    dto.address      = profile.getAddress();
    dto.logoUrl      = profile.getLogoUrl();
    dto.status       = profile.getStatus() != null ? profile.getStatus().name() : null;
    dto.featured     = profile.isFeatured();
    dto.whatsapp  = profile.getWhatsapp();
    dto.latitude  = profile.getLatitude();
    dto.longitude = profile.getLongitude();
    dto.schedule  = profile.getSchedule();
    // Acceder con try/catch por si el proxy no está inicializado
    try {
        dto.category = profile.getCategory() != null ? profile.getCategory().getName() : null;
    } catch (Exception e) {
        dto.category = null;
    }
    try {
        dto.district = profile.getDistrict() != null ? profile.getDistrict().getName() : null;
    } catch (Exception e) {
        dto.district = null;
    }

    return dto;
}

    // Getters
    public Long getId()           { return id; }
    public String getBusinessName(){ return businessName; }
    public String getSlug()       { return slug; }
    public String getDescription(){ return description; }
    public String getPhone()      { return phone; }
    public String getAddress()    { return address; }
    public String getLogoUrl()    { return logoUrl; }
    public String getCategory()   { return category; }
    public String getDistrict()   { return district; }
    public boolean isFeatured()   { return featured; }
    public String getStatus()     { return status; }
    public String getWhatsapp()   { return whatsapp; }
    public Double getLatitude()   { return latitude; }
    public Double getLongitude()  { return longitude; }
    public String getSchedule()   { return schedule; }
    public List<ProfileMedia> getMediaItems() { return mediaItems; }
}