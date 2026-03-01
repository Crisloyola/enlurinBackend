package com.city.auth;

import com.city.profile.Profile;
import com.city.profile.ProfileService;
import com.city.profile.dto.ProfileCreateRequest;
import com.city.profile.dto.ProfilePublicResponse;
import com.city.profile.dto.ProfileResponse;
import com.city.profile.dto.ProfileUpdateRequest;
import com.city.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Perfiles", description = "Gestión de perfiles")
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ProfileResponse createProfile(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody ProfileCreateRequest request
    ) {
        Profile profile = profileService.createProfile(user.getUsername(), request);
        return ProfileResponse.from(profile);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ProfileResponse getMyProfile(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ProfileResponse.from(
                profileService.getMyProfile(user.getUsername())
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public ProfileResponse updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody ProfileUpdateRequest request
    ) {
        return ProfileResponse.from(
                profileService.updateProfile(user.getUsername(), request)
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/logo")
    public ProfileResponse uploadLogo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ProfileResponse.from(
                profileService.uploadLogo(id, user.getUsername(), file)
        );
    }

        @PreAuthorize("hasRole('USER')")
        @PostMapping("/me/banner")
        public ProfileResponse uploadBanner(
                @RequestParam("file") MultipartFile file,
                @AuthenticationPrincipal CustomUserDetails user
        ) {
        return ProfileResponse.from(
                profileService.uploadBanner(user.getUsername(), file)
        );
        }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProfileResponse adminUpdateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request
    ) {
        return ProfileResponse.from(
                profileService.adminUpdateProfile(id, request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }

    @GetMapping("/public/{slug}")
    public ProfileResponse getPublicProfile(@PathVariable String slug) {
        return ProfileResponse.from(
                profileService.getBySlug(slug)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/pending")
    public List<Profile> getPendingProfiles() {
        return profileService.getPendingProfiles();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/approve")
    public Profile approveProfile(@PathVariable Long id) {
        return profileService.approveProfile(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/suspend")
    public Profile suspendProfile(@PathVariable Long id) {
        return profileService.suspendProfile(id);
    }

    @GetMapping("/public")
    public List<ProfilePublicResponse> getPublicProfiles(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q
    ) {
        return profileService.getPublicProfiles(district, category, q);
    }

    @GetMapping("/public/search")
    public List<ProfilePublicResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category
    ) {
        return profileService.searchPublicProfiles(q, category);
    }

    @GetMapping("/public/all")
    public List<ProfilePublicResponse> getAllActiveProfiles() {
        return profileService.getAllActiveProfiles();
    }
}