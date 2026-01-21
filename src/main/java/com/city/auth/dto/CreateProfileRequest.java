package com.city.auth.dto;

public record CreateProfileRequest(
        String businessName,
        String description,
        String phone,
        String address,
        String categorySlug,
        String districtSlug
) {}
