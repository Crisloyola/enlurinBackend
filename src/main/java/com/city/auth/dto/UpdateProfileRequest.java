package com.city.auth.dto;

public record UpdateProfileRequest(
        String description,
        String phone,
        String address,
        String categorySlug,
        String districtSlug
) {}
