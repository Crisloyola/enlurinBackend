package com.city.auth;

import com.city.profile.Profile;
import com.city.profile.ProfileService;
import com.city.profile.dto.ProfileCreateRequest;
import com.city.profile.dto.ProfileResponse;
import com.city.profile.dto.ProfileUpdateRequest;
import com.city.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.tags.Tag;

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

    // 🔐 USER → crear perfil
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ProfileResponse createProfile(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody ProfileCreateRequest request
    ) {
        Profile profile = profileService.createProfile(user.getUsername(), request);
        return ProfileResponse.from(profile);
    }

    // 🔐 USER → ver su perfil
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ProfileResponse getMyProfile(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ProfileResponse.from(
                profileService.getMyProfile(user.getUsername())
        );
    }

    // 🔐 USER → actualizar su perfil
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

    // 🔐 USER → subir logo
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

    // 🔥 ADMIN → actualizar cualquier perfil
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

    // 🔥 ADMIN → eliminar perfil
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }

    // 🌍 PÚBLICO → ver perfil por slug
    @GetMapping("/public/{slug}")
    public ProfileResponse getPublicProfile(@PathVariable String slug) {
        return ProfileResponse.from(
                profileService.getBySlug(slug)
        );
    }
}
