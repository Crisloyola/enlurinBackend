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


@Tag(name = "Perfiles Privados", description = "Gestión de perfiles (USER / ADMIN)")
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final FileStorageService fileStorageService;

    // 🔐 SOLO USUARIO LOGUEADO
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Profile createProfile(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ProfileCreateRequest request
    ) {
        return profileService.createProfile(
                user.getUsername(), // email
                request
        );
    }

    // 🔐 SOLO EL DUEÑO
    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public Profile updateMyProfile(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody ProfileUpdateRequest request
    ) {
        return profileService.updateProfile(user.getUsername(), request);
    }

    // 🔥 SOLO ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Profile adminUpdateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request
    ) {
        return profileService.adminUpdateProfile(id, request);
    }

    // 🔐 USER y ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/logo")
    public Profile uploadLogo(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) {
        return profileService.uploadLogo(id, file);
    }
}