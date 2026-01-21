package com.city.profile;

/**
 * DTO para actualización parcial del perfil
 * (PATCH-like)
 */
public record ProfileUpdateRequest(

        String description,
        String phone,
        String address,
        String categorySlug,
        String districtSlug

) {}