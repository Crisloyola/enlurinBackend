package com.city.profile.dto;

public class ProfileUpdateRequest {

    private String businessName;
    private String description;
    private String phone;
    private String address;
    private String categorySlug;
    private String districtSlug;
    private String whatsapp;
    private Double latitude;
    private Double longitude;
    private String schedule;

    public String getBusinessName()  { return businessName; }
    public String getDescription()   { return description; }
    public String getPhone()         { return phone; }
    public String getAddress()       { return address; }
    public String getCategorySlug()  { return categorySlug; }
    public String getDistrictSlug()  { return districtSlug; }

    // ← SETTERS FALTABAN
    public void setBusinessName(String v)  { this.businessName = v; }
    public void setDescription(String v)   { this.description = v; }
    public void setPhone(String v)         { this.phone = v; }
    public void setAddress(String v)       { this.address = v; }
    public void setCategorySlug(String v)  { this.categorySlug = v; }
    public void setDistrictSlug(String v)  { this.districtSlug = v; }
    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String v) { this.whatsapp = v; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double v) { this.latitude = v; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double v) { this.longitude = v; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String v) { this.schedule = v; }

}