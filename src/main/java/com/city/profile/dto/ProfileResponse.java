package com.city.profile.dto;

import java.util.List;

import com.city.profile.Profile;
import com.city.profile.ProfileMedia;

public class ProfileResponse {

    private Long id;
    private String businessName;
    private String slug;
    private String description;
    private String phone;
    private String address;
    private String logoUrl;
    private String bannerUrl;
    private String category;
    private String district;
    private String status;
    private boolean featured;
    private String whatsapp;
    private Double latitude;
    private Double longitude;
    private String schedule;
    private List<ProfileMedia> mediaItems;

    public ProfileResponse(Long id, String businessName, String slug,
                           String description, String phone, String address,
                           String logoUrl, String bannerUrl, String category,
                           String district, String status, boolean featured, String whatsapp, Double latitude, Double longitude, String schedule, List<ProfileMedia> mediaItems) {
        this.id           = id;
        this.businessName = businessName;
        this.slug         = slug;
        this.description  = description;
        this.phone        = phone;
        this.address      = address;
        this.logoUrl      = logoUrl;
        this.bannerUrl    = bannerUrl;
        this.category     = category;
        this.district     = district;
        this.status       = status;
        this.featured     = featured;
        this.whatsapp     = whatsapp;
        this.latitude     = latitude;
        this.longitude    = longitude;
        this.schedule     = schedule;
        this.mediaItems   = mediaItems;
    }

    public Long getId()            { return id; }
    public String getBusinessName(){ return businessName; }
    public String getSlug()        { return slug; }
    public String getDescription() { return description; }
    public String getPhone()       { return phone; }
    public String getAddress()     { return address; }
    public String getLogoUrl()     { return logoUrl; }
    public String getBannerUrl()   { return bannerUrl; }
    public String getCategory()    { return category; }
    public String getDistrict()    { return district; }
    public String getStatus()      { return status; }
    public boolean isFeatured()    { return featured; }
    public String getWhatsapp() { return whatsapp; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getSchedule() { return schedule; }
    public List<ProfileMedia> getMediaItems() { return mediaItems; }

    public static ProfileResponse from(Profile p) {
        return new ProfileResponse(
            p.getId(),
            p.getBusinessName(),
            p.getSlug(),
            p.getDescription(),
            p.getPhone(),
            p.getAddress(),
            p.getLogoUrl(),
            p.getBannerUrl(),
            p.getCategory() != null ? p.getCategory().getName() : null,
            p.getDistrict()  != null ? p.getDistrict().getName()  : null,
            p.getStatus()    != null ? p.getStatus().name()        : null,
            p.isFeatured(),
            p.getWhatsapp(),
            p.getLatitude(),
            p.getLongitude(),
            p.getSchedule(),
            p.getMediaItems()
        );
    }
}