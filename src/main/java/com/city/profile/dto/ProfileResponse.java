package com.city.profile.dto;

public record ProfileResponse(
        Long id,
        String businessName,
        String description,
        String phone,
        String address,
        String slug,
        String logoUrl,
        String category,
        String district
) {}