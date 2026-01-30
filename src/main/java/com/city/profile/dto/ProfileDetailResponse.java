package com.city.profile.dto;

import com.city.profile.Profile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDetailResponse {

    private Long id;
    private String businessName;
    private String description;
    private String phone;
    private String address;
    private String logoUrl;
    private String category;
    private String district;

    public static ProfileDetailResponse from(Profile profile) {
        return ProfileDetailResponse.builder()
                .id(profile.getId())
                .businessName(profile.getBusinessName())
                .description(profile.getDescription())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .logoUrl(profile.getLogoUrl())
                .category(profile.getCategory().getName())
                .district(profile.getDistrict().getName())
                .build();
    }
}
