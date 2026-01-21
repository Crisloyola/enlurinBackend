package com.city.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.city.files.FileStorageService;
import com.city.profile.Profile;

import com.city.profile.ProfileService;
import com.city.profile.dto.ProfileCreateRequest;
import com.city.profile.dto.ProfileUpdateRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Tag(name = "Perfiles Privados")
public class ProfileController {

    private final ProfileService profileService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Profile createProfile(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ProfileCreateRequest request
    ) {
        return profileService.createProfile(user.getUsername(), request);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public Profile updateMyProfile(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ProfileUpdateRequest request
    ) {
        return profileService.updateProfile(user.getUsername(), request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Profile adminUpdateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request
    ) {
        return profileService.adminUpdateProfile(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }
}
